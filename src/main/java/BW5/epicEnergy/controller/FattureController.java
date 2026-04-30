package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.DTO.StatoFatturaDTO;
import BW5.epicEnergy.entity.Fattura;
import BW5.epicEnergy.exception.PayloadValidationException;
import BW5.epicEnergy.service.FattureService;
import BW5.epicEnergy.service.StatoFatturaService;
import BW5.epicEnergy.specifications.FatturaSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    /*@DeleteMapping("/stati/{tipo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStato(@PathVariable String tipo) {
        this.statoFatturaService.delete(tipo);
    }*/

    /*@PutMapping("/stati/{tipo}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public StatoFattura findByTipoAnbUpdate(@PathVariable String tipo, @RequestBody StatoFatturaDTO body) {
        return this.statoFatturaService.finbByTipoAndUpdate(tipo, body);
    }*/


    /*@GetMapping("/{fatturaId}")
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Fattura getById(@PathVariable UUID fatturaId) {
        return this.fattureService.findById(fatturaId);
    }*/


    @GetMapping
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Page<Fattura> ottieniFattureOrdinateEFiltrate(@RequestParam(required = false) UUID idCliente,
                                                         @RequestParam(required = false) String nomeCliente,
                                                         @RequestParam(required = false) String parteNomeCliente,
                                                         @RequestParam(required = false) String stato,
                                                         @RequestParam(required = false) LocalDate dataDopoDi,
                                                         @RequestParam(required = false) LocalDate dataPrimaDi,
                                                         @RequestParam(required = false) LocalDate data,
                                                         @RequestParam(required = false) Integer annoDopoDi,
                                                         @RequestParam(required = false) Integer annoPrimaDi,
                                                         @RequestParam(required = false) Integer anno,
                                                         @RequestParam(required = false) BigDecimal importoMin,
                                                         @RequestParam(required = false) BigDecimal importoMax,
                                                         @RequestParam(required = false) BigDecimal importo,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "numero") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String order) {

        Specification<Fattura> specification = FatturaSpecification.filtra(
                idCliente,
                nomeCliente,
                parteNomeCliente,
                stato,
                dataDopoDi,
                dataPrimaDi,
                data,
                annoDopoDi,
                annoPrimaDi,
                anno,
                importoMin,
                importoMax,
                importo
        );

        return this.fattureService.findAll(specification, page, size, sortBy, order);
    }

    /*@DeleteMapping("/{fatturaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void getByIdAndDelete(@PathVariable UUID fatturaId) {
        this.fattureService.delete(fatturaId);
    }*/


}
