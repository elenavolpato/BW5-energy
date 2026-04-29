package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.NotBlank;

public record RuoliDTO(
        @NotBlank(message = "Devi specificare il suolo che vuoi creare")
        String ruolo
) {
}
