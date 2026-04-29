package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.ClienteDTO;
import BW5.epicEnergy.DTO.EmailDTO;
import BW5.epicEnergy.DTO.IndirizzoDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Indirizzo;
import BW5.epicEnergy.enums.TipoCliente;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.ClientiRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import BW5.epicEnergy.tools.EmailSender;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ClientiService {
    private final ClientiRepository clienteRepository;
    private final IndirizzoService indirizzoService;
    private final Cloudinary cloudinary;
    private final EmailSender emailSender;

    public UUID save(ClienteDTO body) {


        if (this.clienteRepository.existsByPartitaIva(body.partitaIva()))
            throw new BadRequestException("Partita IVA già associata ad un altro cliente");

        Indirizzo sedeLegale;
        Indirizzo sedeOperativa;
        Cliente nuovoCliente = null;
        try {
            sedeLegale = this.indirizzoService.findByViaAndCivicoAndLocalitaAndCapAndComune_Id(
                    body.sedeLegale().via(),
                    body.sedeLegale().civico(),
                    body.sedeLegale().localita(),
                    body.sedeLegale().cap(),
                    body.sedeLegale().comune());
            if (this.clienteRepository.existsBySedeLegaleOrSedeOperativa(sedeLegale, sedeLegale))
                throw new BadRequestException("Sede legale già associata ad un altro cliente");
        } catch (NotFoundException ex) {
            try {
                sedeOperativa = this.indirizzoService.findByViaAndCivicoAndLocalitaAndCapAndComune_Id(
                        body.sedeOperativa().via(),
                        body.sedeOperativa().civico(),
                        body.sedeOperativa().localita(),
                        body.sedeOperativa().cap(),
                        body.sedeOperativa().comune());
                if (this.clienteRepository.existsBySedeLegaleOrSedeOperativa(sedeOperativa, sedeOperativa))
                    throw new BadRequestException("Sede operativa già associata ad un altro cliente");
            } catch (NotFoundException e) {
                sedeLegale = indirizzoService.save(body.sedeLegale());
                try {
                    sedeOperativa = indirizzoService.save(body.sedeOperativa());
                    nuovoCliente = new Cliente(
                            body.ragioneSociale(), body.partitaIva(), body.email(),
                            body.fatturatoAnnuale(), body.pec(), body.telefono(),
                            body.emailContatto(), body.nomeContatto(), body.cognomeContatto(), body.telefonoContatto(), sedeLegale, sedeOperativa, TipoCliente.valueOf(body.tipo())
                    );

                } catch (BadRequestException exception) {
                    nuovoCliente = new Cliente(
                            body.ragioneSociale(), body.partitaIva(), body.email(),
                            body.fatturatoAnnuale(), body.pec(), body.telefono(),
                            body.emailContatto(), body.nomeContatto(), body.cognomeContatto(), body.telefonoContatto(), sedeLegale, sedeLegale, TipoCliente.valueOf(body.tipo())
                    );
                }
            }
        }
        assert nuovoCliente != null;
        Cliente clienteSalvato = this.clienteRepository.save(nuovoCliente);
        log.info("Cliente con id " + clienteSalvato.getId() + " salvato con successo!");
        return clienteSalvato.getId();
    }

    public Cliente findById(String clienteId) {
        return this.clienteRepository.findById(UUID.fromString(clienteId)).orElseThrow(() -> new NotFoundException("customer"));
    }

    public Cliente findById(UUID id) {
        return this.clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("customer"));
    }

    public Page<Cliente> findAll(Specification<Cliente> specification, int page, int size, String sortBy, String order) {
        if (page < 0) page = 0;
        if (size < 0 || size > 100) size = 10;

        String criterioOrdine = switch (sortBy) {
            case "nome" -> "ragioneSociale";
            case "fatturato" -> "fatturatoAnnuale";
            case "inserimento" -> "dataInserimento";
            case "ultimoContatto" -> "dataUltimoContatto";
            case "sedeLegale" -> "sedeLegale.comune.provincia.nome";
            default -> throw new BadRequestException("Criterio di ordinamento non valido");
        };

        Pageable pageable = switch (order) {
            case "asc" -> PageRequest.of(page, size, Sort.by(criterioOrdine));
            case "disc" -> PageRequest.of(page, size, Sort.by(criterioOrdine).reverse());
            default -> throw new BadRequestException("Criterio di ordinamento non valido");
        };

        return this.clienteRepository.findAll(specification, pageable);
    }

    public String inviaEmailACliente(String clienteId, EmailDTO body) {
        Cliente cliente = this.findById(clienteId);
        return this.emailSender.sendEmailToCustomer(cliente, body);
    }

    public String inviaEmailAContattoCliente(String clienteId, EmailDTO body) {
        Cliente cliente = this.findById(clienteId);
        return this.emailSender.sendEmailToCustomerReferent(cliente, body);
    }

    public Cliente update(UUID id, ClienteDTO body) {
        Cliente cliente = this.findById(id);
        cliente.setRagioneSociale(body.ragioneSociale());
        cliente.setPartitaIva(body.partitaIva());
        cliente.setEmail(body.email());
        cliente.setFatturatoAnnuale(body.fatturatoAnnuale());
        cliente.setPec(body.pec());
        cliente.setTelefono(body.telefono());
        cliente.setEmailContatto(body.emailContatto());
        cliente.setNomeContatto(body.nomeContatto());
        cliente.setCognomeContatto(body.cognomeContatto());
        cliente.setTelefonoContatto(body.telefonoContatto());
        cliente.setDataUltimoContatto(LocalDate.now());
        cliente.setTipo(TipoCliente.valueOf(body.tipo()));
        return this.clienteRepository.save(cliente);
    }

    public void delete(UUID id) {
        Cliente cliente = this.findById(id);
        clienteRepository.deleteById(id);
    }

    public Cliente avatarUpload (UUID id, MultipartFile file) {
        Cliente cliente = this.findById(id);
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("public_id", "clienti/" + id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = (String) uploadResult.get("secure_url");
        cliente.setLogoAziendale(url);
        return this.clienteRepository.save(cliente);
    }

    public void deleteCliente(UUID id) {
        System.out.println("-------------- " + id);
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossibile eliminare: cliente non trovato con id: " + id);
        }
        clienteRepository.deleteById(id);
    }


}
