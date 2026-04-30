package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StatoFatturaDTO(
        @NotBlank(message = "Il tipo di stato è obbligatorio")
        @Size(min = 2, max = 255, message = "Il tipo di stato deve avere un numero di caratteri incluso tra 2 e 255")
        String tipo
) {
}
