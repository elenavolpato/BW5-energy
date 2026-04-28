package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String via;
    private String civico;
    private String localita;
    private String cap;

    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;

    public Indirizzo(String via, String civico, String localita, String cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
