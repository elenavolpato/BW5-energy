package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comune {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name="povincia_id", nullable = false)
    private Provincia provincia;
}
