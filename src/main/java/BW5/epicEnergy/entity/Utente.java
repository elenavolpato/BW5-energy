package BW5.epicEnergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "utenti")
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(name = "avatar_url", nullable = false)
    private String avatarURL;

    @JsonIgnore
    @OneToMany(mappedBy = "utente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RuoliUtente> ruoli = new ArrayList<>();
    /*@OneToMany(mappedBy = "utente")
    private List<RuoliUtente> ruoli = new ArrayList<>();*/

    public Utente(String username, String email, String password, String nome, String cognome) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatarURL = "https://ui-avatars.com/api?name=" + nome + "+" + cognome;
    }

    // metodo per aggiungere un ruolo all'utente, si richiama dal service
    /*public void addRuolo(String ruolo) {
        boolean exist = this.ruoli.stream().anyMatch(r -> r.getRuolo().equals(ruolo.trim().toUpperCase()));
        if (!exist) {
            RuoliUtente nuovoRuolo = new RuoliUtente();
            Ruoli newRuolo = new Ruoli(ruolo.trim().toUpperCase());
            nuovoRuolo.setRuolo(newRuolo);
            nuovoRuolo.setUtente(this);
            this.ruoli.add(nuovoRuolo);
        }
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.ruoli.stream().map(r -> new SimpleGrantedAuthority(r.getRuolo().getRuolo())).toList();
    }

}
