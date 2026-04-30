package BW5.epicEnergy.controller;

import BW5.epicEnergy.DTO.*;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.exception.PayloadValidationException;
import BW5.epicEnergy.service.ClientiService;
import BW5.epicEnergy.specifications.ClienteSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clienti")
@AllArgsConstructor
public class ClientiController {
    private final ClientiService clientiService;

    @GetMapping("/{idCliente}")
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public Cliente ottieniCliente(@PathVariable UUID idCliente) {
        return this.clientiService.findById(idCliente);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
    public UUID salvaNuovoCliente(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.clientiService.save(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Cliente aggiornaCliente(@PathVariable UUID id, @RequestBody @Validated UpdateClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.clientiService.update(id, body);
    }

    @PatchMapping("/{id}/sedeLegale")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Cliente aggiornaSedeLegaleCliente(@PathVariable UUID id, @RequestBody @Validated IndirizzoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.clientiService.updateSedeLegale(id, body);
    }

    @PatchMapping("/{id}/sedeOperativa")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Cliente aggiornaSedeOperativaCliente(@PathVariable UUID id, @RequestBody @Validated IndirizzoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new PayloadValidationException(errors);
        }
        return this.clientiService.updateSedeOperativa(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable UUID id) {
        this.clientiService.deleteCliente(id);
    }

    @PatchMapping("/{id}/logo")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Cliente avatarUpload(@PathVariable UUID id,
                                @RequestParam("logo") MultipartFile file) throws IOException {
        return this.clientiService.avatarUpload(id, file);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ADMIN')")
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
                                                         @RequestParam(required = false) String parteNome,
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
                nome,
                parteNome
        );

        return this.clientiService.findAll(specification, page, size, sortBy, order);
    }

    @PostMapping("/{clienteId}/invioEmail")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public InvioEmailDTO inviaEmailACliente(@PathVariable String clienteId, @RequestBody @Validated EmailDTO body) {
        return this.clientiService.inviaEmailACliente(clienteId, body);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/{clienteId}/referente/invioEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public InvioEmailDTO inviaEmailAContattoCliente(@PathVariable String clienteId, @RequestBody @Validated EmailDTO body) {
        return this.clientiService.inviaEmailAContattoCliente(clienteId, body);
    }

}
