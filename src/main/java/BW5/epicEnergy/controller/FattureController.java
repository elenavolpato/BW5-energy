package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.DTO.StatoFatturaDTO;
import BW5.epicEnergy.entity.Fattura;
import BW5.epicEnergy.exception.PayloadValidationException;
import BW5.epicEnergy.service.FattureService;
import BW5.epicEnergy.service.StatoFatturaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fatture")
@AllArgsConstructor
public class FattureController {
    private final FattureService fattureService;
    private final StatoFatturaService statoFatturaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvaNuovaFattura(@RequestBody @Validated FatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.fattureService.save(body);
    }

    @PostMapping("/stati")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvaNuovoStatoFattura(@RequestBody @Validated StatoFatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.statoFatturaService.save(body);
    }

    //Salve capo-gruppo, ho aggiunto queste operazioni.
    @GetMapping
    public List<Fattura> findAll() {
        return this.fattureService.findAll();
    }

    @GetMapping("/{id}")
    public Fattura findById(@PathVariable UUID id) {
        return this.fattureService.findById(id);
    }

    @PutMapping("/{id}")
    public Fattura updateFattura(@PathVariable UUID id, @RequestBody @Validated FatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.fattureService.updateFattura(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFattura(@PathVariable UUID id) {
        this.fattureService.deleteById(id);
    }
}
