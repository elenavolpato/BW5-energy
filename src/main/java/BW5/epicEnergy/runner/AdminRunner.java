package BW5.epicEnergy.runner;

import BW5.epicEnergy.DTO.AssegnazioneRuoloUtenteDTO;
import BW5.epicEnergy.DTO.UtenteDTO;
import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.exception.EmailAlreadyExistsException;
import BW5.epicEnergy.service.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@AllArgsConstructor
public class AdminRunner implements CommandLineRunner {
    private final UtenteService utenteService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("INIZIALIZZAZIONE PRIMO ADMIN ------------------");
        /*Utente primoUtenteSalvato = null;*/
        try {
            UtenteDTO primoUtente = new UtenteDTO(
                    "giorgiaFormicola",
                    "giorgia.formicola97@gmail.com",
                    "Ciaone123",
                    "Giorgia",
                    "Formicola"
            );

            Utente primoUtenteSalvato = this.utenteService.save(primoUtente);
            this.utenteService.assegnaRuoloAUtente(primoUtenteSalvato.getId(), new AssegnazioneRuoloUtenteDTO("ADMIN"));
        } catch (EmailAlreadyExistsException e) {
            System.out.println("Primo utente già salvato nel database!");
        }
        
        System.out.println("Primo admin registrato!");
    }
}
