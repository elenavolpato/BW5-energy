package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fatture")
@Getter
@Setter
@ToString
public class Fattura {
    @Column(nullable = false, unique = true)
    static int numero;
    @Column(nullable = false)
    LocalDate data;

    @Column(nullable = false)
    BigDecimal importo;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    Cliente cliente;
    //TODO:aggiungere relazione many to one con STATI_FATTURE
    @Column(nullable = false)
    String stato;
    @Id
    @GeneratedValue
    private UUID id;

    protected Fattura() {
    }

    //TODO:cambiare costruttore per stato con oggetto Stato
    public Fattura(BigDecimal importo, Cliente cliente) {
        this.data = LocalDate.now();
        this.importo = importo;
        this.cliente = cliente;
        this.stato = "CARICATA";
        numero++;
    }
}
