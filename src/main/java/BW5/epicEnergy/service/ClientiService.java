package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.ClienteDTO;
import BW5.epicEnergy.DTO.IndirizzoDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Indirizzo;
import BW5.epicEnergy.enums.TipoCliente;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.ClientiRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ClientiService {
    private final ClientiRepository clienteRepository;
    private final IndirizzoService indirizzoService;

    public UUID save(ClienteDTO body) {

       Indirizzo sedeL = indirizzoService.save(body.sedeLegale());
        System.out.println("-------------- " + sedeL);
       Indirizzo sedeO = indirizzoService.save(body.sedeOperativa());
        System.out.println("-------------- " + sedeO);


        if (this.clienteRepository.existsByPartitaIva(body.partitaIva()))
            throw new BadRequestException("Partita IVA già associata ad un altro cliente");
        Cliente nuovoCliente = new Cliente(
                body.ragioneSociale(), body.partitaIva(), body.email(),
                body.fatturatoAnnuale(), body.pec(), body.telefono(),
                body.emailContatto(), body.nomeContatto(), body.cognomeContatto(), body.telefonoContatto(), sedeL, sedeO, TipoCliente.valueOf(body.tipo())
        );
        Cliente clienteSalvato = this.clienteRepository.save(nuovoCliente);
        log.info("Cliente con id " + clienteSalvato.getId() + " salvato con successo!");
        return clienteSalvato.getId();
    }

    public Cliente findById(String clienteId) {
        return this.clienteRepository.findById(UUID.fromString(clienteId)).orElseThrow(() -> new NotFoundException("customer"));
    }

    //aggiungo operazioni CRUD
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Cliente findById(UUID id) {
        return this.clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("customer"));
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
}
