package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.UtenteDTO;
import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.service.UtenteService;
import BW5.epicEnergy.tools.EmailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService, EmailSender emailSender) {
        this.utenteService = utenteService;

    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "surname") String sortBy) {
        return this.utenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/me")
    public Utente getOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public Utente updateOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser, @RequestBody UtenteDTO body) {
        return this.utenteService.update(currentAuthenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utenteService.delete(currentAuthenticatedUser.getId());
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente getById(@PathVariable UUID userId) {
        return this.utenteService.findById(userId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente getByIdAndUpdate(@PathVariable UUID userId, @RequestBody UtenteDTO body) {
        return this.utenteService.update(userId, body);
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void getByIdAndDelete(@PathVariable UUID utenteId) {
        this.utenteService.delete(utenteId);
    }

    /*@PostMapping("/invioEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public String inviaEmail(@PathVariable String clienteId, @RequestBody @Validated EmailDTO body) {
        return this.clientiService.inviaEmailAContattoCliente(clienteId, body);
    }*/
}
