package BW5.epicEnergy.DTO;


import BW5.epicEnergy.entity.Indirizzo;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ClienteDTO(
        @NotBlank(message = "La ragione sociale è obbligatoria")
        @Size(min = 2, max = 255, message = "La ragione sociale deve contenere tra i 2 ed i 255 caratteri")
        String ragioneSociale,

        @NotBlank(message = "La partita IVA è obbligatoria")
        @Pattern(regexp = "^\\d{11}$", message = "La partita IVA deve contenere 11 caratteri numerici")
        String partitaIva,

        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email deve rispettare un formato email valido")
        String email,

        @PositiveOrZero(message = "Il fatturato annuale deve essere una numero positivo o uguale a zero")
        BigDecimal fatturatoAnnuale,

        @NotBlank(message = "La PEC è obbligatoria")
        @Email(message = "La PEC deve rispettare un formato email valido")
        String pec,

        @NotBlank(message = "Il numero di telefono è obbligatorio")
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Il numero di telefono deve rispettare un formato valido")
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
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Il numero di telefono del contatto deve rispettare un formato valido")
        String telefonoContatto,

        IndirizzoDTO sedeLegale,
        IndirizzoDTO sedeOperativa,

        @NotBlank(message = "Il tipo di cliente è obbligatorio")
        @Pattern(regexp = "^(PA|SAS|SPA|SRL)$", message = "Tipo di cliente non valido")
        String tipo
) {
}
