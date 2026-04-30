package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "stati_fatture")
@Getter
@Setter
@ToString
public class StatoFattura {
    @Column(nullable = false, unique = true)
    String tipo;
    @Id
    @GeneratedValue
    private UUID id;

    protected StatoFattura() {
    }

    public StatoFattura(String tipo) {
        this.tipo = tipo;
    }
}
