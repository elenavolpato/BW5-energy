package BW5.epicEnergy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(unique = true)
    private String sigla;
}
