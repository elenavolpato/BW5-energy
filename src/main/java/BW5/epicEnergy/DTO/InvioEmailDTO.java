package BW5.epicEnergy.DTO;

import java.time.LocalDateTime;

public record InvioEmailDTO(
        String message,
        LocalDateTime timestamp
) {
}
