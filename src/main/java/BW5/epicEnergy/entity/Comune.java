package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "codice_province")
    private String codiceProvincia;

    @Column(name = "progressivo_comuni")
    private String progressivoComune;

    @ManyToOne
    @JoinColumn(name = "provincia_sigla", referencedColumnName = "sigla")
    private Provincia provincia;
}
