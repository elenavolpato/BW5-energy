package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.RuoliDTO;
import BW5.epicEnergy.entity.Ruoli;
import BW5.epicEnergy.exception.ValidationExceptions;
import BW5.epicEnergy.service.RuoliService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ruoli")
public class RuoliController {

    private final RuoliService ruoliService;


    public RuoliController(RuoliService ruoliService) {
        this.ruoliService = ruoliService;
    }


    @PostMapping("/creaNuovo")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String creaRuolo(@RequestBody @Validated RuoliDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList();
            throw new ValidationExceptions(errors);
        }

        return "La creazione del nuovo ruolo: " + this.ruoliService.create(body).getRuolo() + " è avvenuta con successo";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Page<Ruoli> getRuoli(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "ruolo") String sortBy) {
        return this.ruoliService.findAll(page, size, sortBy);
    }

    @DeleteMapping("/{ruolo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable String ruolo) {
        this.ruoliService.delete(ruolo);
    }


}
