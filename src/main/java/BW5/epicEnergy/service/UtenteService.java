package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.EmailDTO;
import BW5.epicEnergy.DTO.UtenteDTO;
import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.exception.EmailAlreadyExistsException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.UtenteRepository;
import BW5.epicEnergy.tools.EmailSender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder bcrypt;
    private final EmailSender emailSender;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder bcrypt, EmailSender emailSender) {
        this.utenteRepository = utenteRepository;
        this.bcrypt = bcrypt;
        this.emailSender = emailSender;
    }


    public Utente save(UtenteDTO body) {

        if (this.utenteRepository.existsByEmail((body.email())))
            throw new EmailAlreadyExistsException("L'email" + body.email() + " con cui stai provando a registrarti è già associata ad un altro utente.");

        Utente newU = this.utenteRepository.save(new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome()));

        // qia assegno di default il ruolo di UTENTE
        newU.addRuolo("utente");

        return newU;
    }

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException("utente"));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente"));
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 10;
        if (size < 0) size = 1;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    public Utente update(UUID utenteId, UtenteDTO body) {
        Utente toUpdate = this.findById(utenteId);

        if (!toUpdate.getEmail().equals(body.email()))
            throw new EmailAlreadyExistsException("L'email" + body.email() + " con cui stai provando a registrarti è già associata ad un altro utente.");

        toUpdate.setUsername(body.username());
        toUpdate.setEmail(body.email());
        toUpdate.setPassword(bcrypt.encode(body.password()));
        toUpdate.setNome(body.nome());
        toUpdate.setCognome(body.cognome());

        return toUpdate;
    }

    public void delete(UUID utenteId) {
        this.utenteRepository.delete(this.findById(utenteId));
    }

    public String inviaEmail(Utente currentAuthenticatedUser, EmailDTO body) {
        return this.emailSender.sendEmail(currentAuthenticatedUser, body);
    }

}
