package BW5.epicEnergy.security;

import BW5.epicEnergy.entity.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenTools {

    private final String secret;

    public TokenTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }


    public String generateToken(Utente utente) {

    }


}
