package BW5.epicEnergy.controller;

import BW5.epicEnergy.service.UtenteService;
import BW5.epicEnergy.tools.EmailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService, EmailSender emailSender) {
        this.utenteService = utenteService;

    }


    /*@PostMapping("/invioEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public String inviaEmail(@PathVariable String clienteId, @RequestBody @Validated EmailDTO body) {
        return this.clientiService.inviaEmailAContattoCliente(clienteId, body);
    }*/
}
