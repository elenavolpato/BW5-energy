package BW5.epicEnergy.security;

import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenTools {

    private final int durata = 1000 * 60 * 60 * 24 * 7;
    private final String secret;

    public TokenTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }


    public String generateToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // data inizio
                .expiration(new Date(System.currentTimeMillis() + durata)) // data fine (con durata)
                .subject(String.valueOf(utente.getId())) // entita a cui di riferisce
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // codice segreto per generare
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Si sono verificati problemi con il token, riprova il login");
        }
    }

    public UUID extractFromToken(String token) {
        return UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}
