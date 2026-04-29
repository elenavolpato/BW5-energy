package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "ruoli")
public class Ruoli {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String ruolo;

    public Ruoli(String ruolo) {
        this.ruolo = ruolo;
    }
}
