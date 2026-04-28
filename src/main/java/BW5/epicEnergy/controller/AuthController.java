package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.LoginDTO;
import BW5.epicEnergy.DTO.LoginRespDTO;
import BW5.epicEnergy.DTO.NewUtenteRespDTO;
import BW5.epicEnergy.DTO.UtenteDTO;
import BW5.epicEnergy.service.AuthService;
import BW5.epicEnergy.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtenteService utenteService;

    public AuthController(AuthService authService, UtenteService utenteService) {
        this.authService = authService;
        this.utenteService = utenteService;
    }


    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) //201
    public NewUtenteRespDTO saveUtente(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList();
            throw new ValidationExceptions
        }
    }


}
