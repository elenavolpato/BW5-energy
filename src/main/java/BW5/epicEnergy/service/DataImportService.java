package BW5.epicEnergy.service;

import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.entity.Provincia;
import BW5.epicEnergy.repositories.ComuneRepository;
import BW5.epicEnergy.repositories.ProvinciaRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class DataImportService {
    @Autowired
    private ProvinciaRepository provinciaRepo;

    @Autowired
    private ComuneRepository comuneRepo;

    // import province
    public void importProvince(InputStream inputStream) throws Exception {
        // using ; as separator
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(parser)
                .withSkipLines(1) // Skip the header row
                .build()) {
            String[] fields;

            while ((fields = reader.readNext()) != null) {
                // check if line is empty
                if(fields.length >= 2) {
                    String sigla = fields[0].trim(); // use trim to avoid extra spaces
                    String nome = fields[1].trim();
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

    // import comune

    public void importComuni(InputStream inputStream) throws Exception {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(parser)
                .withSkipLines(1)
                .build()){

        String[] fields;
        while ((fields = reader.readNext()) != null) {
            if(fields.length >= 4){
                String nomeComune = fields[2].trim();
                String nomeProvincia = fields[3].trim();

                // avoid duplicates
                if(comuneRepo.findByNome(nomeComune).isEmpty()){
                    Optional<Provincia> prov = provinciaRepo.findProvinciaByNome(nomeProvincia);
                    if(prov.isPresent()){
                        Comune c = new Comune();
                        c.setNome(nomeProvincia);
                        c.setProvincia(prov.get());
                        comuneRepo.save(c);
                    } else {
                        System.out.println("Could not find province: " + nomeProvincia);
                        }
                    }
                }
            }
        }
    }
}
