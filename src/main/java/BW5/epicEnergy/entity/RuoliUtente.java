package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ruoli_utenti")
public class RuoliUtente {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "ruolo_id", nullable = false)
    private Ruoli ruolo;

    protected RuoliUtente() {
    }

    public RuoliUtente(Ruoli ruolo, Utente utente) {
        this.ruolo = ruolo;
        this.utente = utente;
    }
}
