package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.exception.PayloadValidationException;
import BW5.epicEnergy.service.FattureService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvaNuovaFattura(@RequestBody @Validated FatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.fattureService.save(body);
    }
}
