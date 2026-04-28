package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.ClienteDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.exception.PayloadValidationException;
import BW5.epicEnergy.service.ClientiService;
import BW5.epicEnergy.specifications2.ClienteSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    /*@GetMapping
    public Page<Cliente> ottieniClientiOrdinati(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "nome") String sortBy,
                                                @RequestParam(defaultValue = "asc") String order) {
        return this.clientiService.findAll(page, size, sortBy, order);
    }*/

    @GetMapping()
    public Page<Cliente> ottieniClientiOrdinatiEFiltrati(@RequestParam(required = false) BigDecimal fatturatoMin,
                                                         @RequestParam(required = false) BigDecimal fatturatoMax,
                                                         @RequestParam(required = false) BigDecimal fatturato,
                                                         @RequestParam(required = false) LocalDate dataInserimentoDopoDi,
                                                         @RequestParam(required = false) LocalDate dataInserimentoPrimaDi,
                                                         @RequestParam(required = false) LocalDate dataInserimento,
                                                         @RequestParam(required = false) LocalDate dataUltimoContattoDopoDi,
                                                         @RequestParam(required = false) LocalDate dataUltimoContattoPrimaDi,
                                                         @RequestParam(required = false) LocalDate dataUltimoContatto,
                                                         @RequestParam(required = false) String nome,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "nome") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String order) {

        Specification<Cliente> specification = ClienteSpecification.filtra(
                fatturatoMin,
                fatturatoMax,
                fatturato,
                dataInserimentoDopoDi,
                dataInserimentoPrimaDi,
                dataInserimento,
                dataUltimoContattoDopoDi,
                dataUltimoContattoPrimaDi,
                dataUltimoContatto,
                nome
        );

        return this.clientiService.findAll(specification, page, size, sortBy, order);
    }
}
