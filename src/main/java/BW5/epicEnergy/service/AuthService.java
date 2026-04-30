package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.LoginDTO;
import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.exception.UnauthorizedException;
import BW5.epicEnergy.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtenteService utenteService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UtenteService utenteService, TokenTools tokenTools, PasswordEncoder bcrypt) {
        this.utenteService = utenteService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }


    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            Utente logger = this.utenteService.findByEmail(body.email());

            if (this.bcrypt.matches(body.password(), logger.getPassword())) {
                return this.tokenTools.generateToken(logger);
            } else {
                throw new UnauthorizedException("Credenziali errate");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate");
        }
    }


}
