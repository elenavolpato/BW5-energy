package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateClienteDTO(
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email deve rispettare un formato valido")
        String email,

        @NotNull(message = "Il fatturato annuale è obbligatorio")
        @PositiveOrZero(message = "Il fatturato annuale deve essere una numero positivo o uguale a zero")
        BigDecimal fatturatoAnnuale,

        @NotBlank(message = "La pec è obbligatoria")
        @Email(message = "La pec deve rispettare un formato valido")
        String pec,

        @NotBlank(message = "Il numero di telefono è obbligatorio")
        @Pattern(regexp = "^\\d+$", message = "Il numero di telefono deve essere composto solo da caratteri numerici")
        String telefono,

        @NotBlank(message = "L'email del contatto è obbligatoria")
        @Email(message = "L'email del contatto deve rispettare un formato valido")
        String emailContatto,

        @NotBlank(message = "Il nome del contatto è obbligatorio")
        @Size(min = 2, max = 255, message = "Il nome del contatto deve contenere tra i 2 ed i 255 caratteri")
        String nomeContatto,

        @NotBlank(message = "Il cognome del contatto è obbligatorio")
        @Size(min = 2, max = 255, message = "Il cognome del contatto deve contenere tra i 2 ed i 255 caratteri")
        String cognomeContatto,

        @NotBlank(message = "Il numero di telefono del contatto è obbligatorio")
        @Pattern(regexp = "^\\d+$", message = "Il numero di telefono deve essere composto solo da caratteri numerici")
        String telefonoContatto
) {
}
