package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.AssegnazioneRuoloUtenteDTO;
import BW5.epicEnergy.DTO.EmailDTO;
import BW5.epicEnergy.DTO.InvioEmailDTO;
import BW5.epicEnergy.DTO.UtenteDTO;
import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.service.ClientiService;
import BW5.epicEnergy.service.UtenteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService, ClientiService clientiService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "cognome") String sortBy) {
        return this.utenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Utente getOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        System.out.println(currentAuthenticatedUser.getNome());
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Utente updateOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser, @RequestBody UtenteDTO body) {
        return this.utenteService.update(currentAuthenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utenteService.delete(currentAuthenticatedUser.getId());
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Utente getById(@PathVariable UUID utenteId) {
        return this.utenteService.findById(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Utente getByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody UtenteDTO body) {
        return this.utenteService.update(utenteId, body);
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void getByIdAndDelete(@PathVariable UUID utenteId) {
        this.utenteService.delete(utenteId);
    }

    @PostMapping("/invioEmail")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public InvioEmailDTO inviaEmail(@AuthenticationPrincipal Utente currentAuthenticatedUser, @RequestBody @Validated EmailDTO body) {
        return this.utenteService.inviaEmail(currentAuthenticatedUser, body);
    }

    @PostMapping("/{utenteId}/autorizzazioni")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public InvioEmailDTO assegnaNuovoRuolo(@PathVariable UUID utenteId, @RequestBody @Validated AssegnazioneRuoloUtenteDTO body) {
        return this.utenteService.assegnaRuoloAUtente(utenteId, body);
    }

    @DeleteMapping("/{utenteId}/autorizzazioni/{ruolo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void eliminaRuoloUtente(@PathVariable UUID utenteId, @PathVariable String ruolo) {
        this.utenteService.eliminaRuoloAUtente(utenteId, ruolo);
    }

}
