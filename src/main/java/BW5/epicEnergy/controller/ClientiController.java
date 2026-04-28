package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.ClienteDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.exception.PayloadValidationException;
import BW5.epicEnergy.service.ClientiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clienti")
@AllArgsConstructor
public class ClientiController {
    private final ClientiService clientiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvaNuovoCliente(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.clientiService.save(body);
    }

    @GetMapping
    public List<Cliente> findAll() {
        return this.clientiService.findAll();
    }

    @PutMapping("/{id}")
    public Cliente aggiornaCliente(@PathVariable UUID id, @RequestBody @Validated ClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.clientiService.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable UUID id) {
        this.clientiService.delete(id);
    }
}
