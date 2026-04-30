package BW5.epicEnergy.DTO;

import BW5.epicEnergy.entity.StatoFattura;
import jakarta.validation.constraints.NotNull;

public record NuovoStatoFatturaDTO(

        @NotNull(message = "Inserire uno stato fattura esistente")
        StatoFattura stato
) {
}
