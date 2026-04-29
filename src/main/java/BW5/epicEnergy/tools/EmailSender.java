package BW5.epicEnergy.tools;

import BW5.epicEnergy.DTO.EmailDTO;
import BW5.epicEnergy.DTO.InvioEmailDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Utente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailSender {
    private final String domainName;
    private final String apiKey;

    public EmailSender(@Value("${mailgun.domainName}") String domainName, @Value("${mailgun.apiKey}") String apiKey) {
        this.domainName = domainName;
        this.apiKey = apiKey;
    }

    public InvioEmailDTO sendEmailToCustomer(Cliente recipient, EmailDTO body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", body.emailMittente() == null ? "Epic Energy Services <epic.energy.services@administration.com>" : body.emailMittente())
                .queryString("to", recipient.getEmail()) // <-- DEVE ESSERE IL DESTINATARIO VERIFICATO!
                .queryString("subject", body.oggetto() == null || body.oggetto().isEmpty() ? "" : body.oggetto())
                .queryString("text", body.testo())
                .asJson();
        System.out.println(response.getBody());
        return new InvioEmailDTO("Email inviata all'indirizzo " + body.emailMittente() + " inviata con successo!", LocalDateTime.now());
    }

    public InvioEmailDTO sendEmailToCustomerReferent(Cliente recipient, EmailDTO body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", body.emailMittente() == null ? "Epic Energy Services <epic.energy.services@administration.com>" : body.emailMittente())
                .queryString("to", recipient.getEmailContatto()) // <-- DEVE ESSERE IL DESTINATARIO VERIFICATO!
                .queryString("subject", body.oggetto() == null || body.oggetto().isEmpty() ? "" : body.oggetto())
                .queryString("text", body.testo())
                .asJson();
        System.out.println(response.getBody());
        return new InvioEmailDTO("Email inviata all'indirizzo " + body.emailMittente() + " inviata con successo!", LocalDateTime.now());
    }

    public InvioEmailDTO sendEmail(Utente recipient, EmailDTO body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", body.emailMittente() == null ? (recipient.getNome() + " " + recipient.getCognome() + " from Epic Energy Services <" + recipient.getNome().toLowerCase() + recipient.getCognome().toLowerCase() + "@epicenergyservices.com>") : body.emailMittente())
                .queryString("to", body.emailDestinatario()) // <-- DEVE ESSERE IL DESTINATARIO VERIFICATO!
                .queryString("subject", body.oggetto() == null || body.oggetto().isEmpty() ? "" : body.oggetto())
                .queryString("text", body.testo())
                .asJson();
        System.out.println(response.getBody());
        return new InvioEmailDTO("Email inviata all'indirizzo " + body.emailDestinatario() + " inviata con successo!", LocalDateTime.now());
    }
}
