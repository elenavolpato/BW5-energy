package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailDTO(
        @Email(message = "L'email del mittente deve seguire un formato email valido")
        String emailMittente,

        @Email(message = "L'email del destinatario deve seguire un formato email valido")
        String emailDestinatario,

        @Size(max = 500, message = "L'oggetto non può superare i 500 caratteri")
        String oggetto,

        @NotBlank(message = "Il contenuto dell'email è obbligatorio")
        String testo
) {
}
