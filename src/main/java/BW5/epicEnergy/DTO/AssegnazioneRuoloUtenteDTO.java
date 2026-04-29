package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.NotBlank;

public record AssegnazioneRuoloUtenteDTO(
        @NotBlank(message = "Il ruolo è obbligatorio")
        String ruolo/*,

        @NotBlank(message = "L'id dell'utente è obbligatorio")
        UUID idUtente*/
) {
}
