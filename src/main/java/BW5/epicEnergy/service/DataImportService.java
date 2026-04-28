package BW5.epicEnergy.service;

import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.entity.Provincia;
import BW5.epicEnergy.repositories.ComuneRepository;
import BW5.epicEnergy.repositories.ProvinciaRepository;
import BW5.epicEnergy.utils.DataUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class DataImportService {
    @Autowired
    private ProvinciaRepository provinciaRepo;

    @Autowired
    private ComuneRepository comuneRepo;

    // using ";" as separator
    CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();

    // import province
    public void importProvince(InputStream inputStream) throws Exception {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(parser)
                .withSkipLines(1) // Skip the header row
                .build()) {
            String[] fields;

            while ((fields = reader.readNext()) != null) {
                // check if line is empty
                if(fields.length >= 2) {
                    String sigla =  DataUtils.normalize(fields[1]);
                    String nome =  DataUtils.normalize(fields[0]);
                    // handle duplicates
                    if (provinciaRepo.findBySigla(sigla).isEmpty()) {
                        Provincia p = new Provincia();
                        p.setSigla(sigla);
                        p.setNome(nome);
                        provinciaRepo.save(p);
                    }
                }
            }
        }
    }

    public String getSiglaById(Long id) {
        return provinciaRepo.findById(id)
                .map(Provincia::getSigla) // Extracts the sigla if the province is found
                .orElseThrow(() -> new EntityNotFoundException("Province not found with id: " + id));
    }
    // import comuni
    public void importComuni(InputStream inputStream) throws Exception {
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(parser)
                .withSkipLines(1)
                .build()){

        String[] fields;
        while ((fields = reader.readNext()) != null) {
            if(fields.length >= 4){
                String codiceProv = fields[0];
                String progressivoComune = fields[1];
                String nomeComune = fields[2].trim();
                String nomeProvincia =  fields[3].trim();


                // avoid duplicates
                if(comuneRepo.findByNome(nomeComune).isEmpty()){
                    Optional<Provincia> prov = provinciaRepo.findByNomeIgnoreCase(nomeProvincia);
                    if(prov.isPresent()){
                        String sigla = getSiglaById(prov.get().getId());
                        Comune c = new Comune();
                        c.setCodiceProvincia(codiceProv);
                        c.setProgressivoComune(progressivoComune);
                        c.setNome(nomeComune);
                        c.setProvincia(prov.get());
                        comuneRepo.save(c);
                    } else {


                       // System.out.println("Could not find province: " + nomeProvincia);
                        }
                    }
                }
            }
        }
    }
}
