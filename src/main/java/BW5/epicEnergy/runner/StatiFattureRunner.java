package BW5.epicEnergy.runner;

import BW5.epicEnergy.DTO.StatoFatturaDTO;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.service.StatoFatturaService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Order(1)
public class StatiFattureRunner implements CommandLineRunner {
    private final StatoFatturaService statoFatturaService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("INIZIALIZZAZIONE STATI FATTURE ------------------");
        List<StatoFatturaDTO> listaStatiFatture = new ArrayList<>(List.of(
                new StatoFatturaDTO("CREATA"),
                new StatoFatturaDTO("EMESSA"),
                new StatoFatturaDTO("ACCETTATA"),
                new StatoFatturaDTO("PAGATA"),
                new StatoFatturaDTO("RIFIUTATA")
        ));

        listaStatiFatture.forEach(statoFatturaDTO -> {
            try {
                statoFatturaService.save(statoFatturaDTO);
            } catch (BadRequestException e) {
                System.out.println("Stato fattura " + statoFatturaDTO.tipo() + " già presente nel database");
            }
        });
        System.out.println("Stati fatture inizializzati con successo!");
    }
}
