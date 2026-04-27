package BW5.epicEnergy.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FatturaDTO(
        @Positive(message = "L'importo deve essere una numero positivo")
        BigDecimal importo,
        @NotBlank(message = "L'id del cliente è obbligatorio")
        @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}", message = "L'id del cliente deve rispettare un formato UUID valido")
        String clienteId
) {
}
