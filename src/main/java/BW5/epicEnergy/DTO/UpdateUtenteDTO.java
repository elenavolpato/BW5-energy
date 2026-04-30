package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUtenteDTO(
        @NotBlank(message = "Impostare uno username")
        @Size(min = 2, message = "Non puoi impostare uno username di lunghezza inferiore ai due caratteri")
        String username,

        @NotBlank(message = "L'email è necessaria per la registrazione, inserisci un indirizzo email")
        @Email(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$",
                message = "L'email inserita non è nel formato corretto, inserire una mail valida")
        String email,

        @NotBlank(message = "Il nome proprio è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome proprio deve essere compreso tra i 2 e i 30 caratteri")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra i 2 e i 30 caratteri")
        String cognome
) {

}
