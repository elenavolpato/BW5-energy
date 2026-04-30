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
    static long totaleFatture;

    @Column(nullable = false)
    LocalDate data;

    @Column(nullable = false)
    BigDecimal importo;

    @Column(nullable = false, unique = true)
    long numero;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_stato", nullable = false)
    StatoFattura stato;

    @Id
    @GeneratedValue
    private UUID id;

    protected Fattura() {
    }

    public Fattura(BigDecimal importo, Cliente cliente, StatoFattura stato) {
        this.data = LocalDate.now();
        this.importo = importo;
        this.numero = totaleFatture;
        this.cliente = cliente;
        this.stato = stato;
        totaleFatture++;
    }
}
