package BW5.epicEnergy.runner;

import BW5.epicEnergy.DTO.RuoliDTO;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.service.RuoliService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
@AllArgsConstructor
public class RuoliRunner implements CommandLineRunner {
    private final RuoliService ruoliService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("INIZIALIZZAZIONE RUOLI ------------------");
        List<RuoliDTO> listaStatiFatture = new ArrayList<>(List.of(
                new RuoliDTO("UTENTE"),
                new RuoliDTO("ADMIN")
        ));

        listaStatiFatture.forEach(ruoloDTO -> {
            try {
                ruoliService.create(ruoloDTO);
            } catch (BadRequestException e) {
                System.out.println("Ruolo " + ruoloDTO.ruolo() + " già presente nel database!");
            }
        });
        System.out.println("Ruoli inizializzati con successo!");

    }
}
