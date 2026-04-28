package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record IndirizzoDTO (
        @NotBlank
        @Size(min=3, max=255)
        String via,

        @NotBlank
        @Size(min=1)
        String civico,

        @NotBlank
        String localita,

        @NotBlank
        @Size(min=5, max=5)
        @Pattern(regexp = "^\\d+$", message = "Solo numeri permessi")
        String cap,

        @NotBlank
        @Positive
        Long comune
){}
