package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AssegnazioneRuoloUtenteDTO(
        @NotBlank(message = "Il ruolo è obbligatorio")
        String ruolo,

        @NotBlank(message = "L'id dell'utente è obbligatorio")
        UUID idUtente
) {
}
