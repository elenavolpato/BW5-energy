package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="comuni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comune {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "provincia_sigla", referencedColumnName = "sigla")
    private Provincia provincia;
}
