package BW5.epicEnergy.entity;

import BW5.epicEnergy.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@ToString
public class Cliente {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "ragione_sociale", nullable = false)
    private String ragioneSociale;

    @Column(name = "partita_iva", nullable = false, length = 11, unique = true)
    private String partitaIva;

    @Column(nullable = false)
    private String email;

    @Column(name = "data_inserimento", nullable = false)
    private LocalDate dataInserimento;

    @Column(name = "data_ultimo_contatto", nullable = false)
    private LocalDate dataUltimoContatto;

    @Column(name = "fatturato_annuale", nullable = false)
    private BigDecimal fatturatoAnnuale;

    @Column(nullable = false)
    private String pec;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "email_contatto", nullable = false)
    private String emailContatto;

    @Column(name = "nome_contatto", nullable = false)
    private String nomeContatto;

    @Column(name = "cognome_contatto", nullable = false)
    private String cognomeContatto;

    @Column(name = "telefono_contatto", nullable = false)
    private String telefonoContatto;

    @Column(name = "logo_aziendale", nullable = false)
    private String logoAziendale;

    @OneToOne
    @JoinColumn(name = "sede_legale_id", nullable = false)
    private Indirizzo sedeLegale;

    @OneToOne
    @JoinColumn(name = "sede_operativa_id", nullable = false)
    private Indirizzo sedeOperativa;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCliente tipo;

    protected Cliente() {
    }

    public Cliente(String ragioneSociale, String partitaIva, String email, BigDecimal fatturatoAnnuale, String pec, String telefono,
                   String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto,
                   Indirizzo sedeLegale, Indirizzo sedeOperativa, TipoCliente tipo) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = LocalDate.now();
        this.dataUltimoContatto = this.dataInserimento;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = "https://res.cloudinary.com/giorgiaf/image/upload/q_auto/f_auto/v1777288904/epic_energy_services_logo_round_23bcea.png";
        this.sedeLegale = sedeLegale;
        this.sedeOperativa = sedeOperativa;
        this.tipo = tipo;
    }
}
