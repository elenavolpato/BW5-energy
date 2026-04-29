package BW5.epicEnergy.tools;

import BW5.epicEnergy.DTO.EmailDTO;
import BW5.epicEnergy.entity.Cliente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private final String domainName;
    private final String apiKey;

    public EmailSender(@Value("${mailgun.domainName}") String domainName, @Value("${mailgun.apiKey}") String apiKey) {
        this.domainName = domainName;
        this.apiKey = apiKey;
    }

    public String sendEmailToCustomer(Cliente recipient, EmailDTO body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", body.emailMittente() == null ? "Epic Energy Services <epic.energy.services@administration.com>" : body.emailMittente())
                .queryString("to", recipient.getEmail()) // <-- DEVE ESSERE IL DESTINATARIO VERIFICATO!
                .queryString("subject", body.oggetto() == null || body.oggetto().isEmpty() ? "" : body.oggetto())
                .queryString("text", body.testo())
                .asJson();
        System.out.println(response.getBody());
        return response.getBody().toPrettyString();
    }

    public String sendEmailToCustomerReferent(Cliente recipient, EmailDTO body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", body.emailMittente() == null ? "Epic Energy Services <epic.energy.services@administration.com>" : body.emailMittente())
                .queryString("to", recipient.getEmailContatto()) // <-- DEVE ESSERE IL DESTINATARIO VERIFICATO!
                .queryString("subject", body.oggetto() == null || body.oggetto().isEmpty() ? "" : body.oggetto())
                .queryString("text", body.testo())
                .asJson();
        System.out.println(response.getBody());
        return response.getBody().toPrettyString();
    }

    public String sendGenericEmail(EmailDTO recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", recipient.emailMittente() == null ? "Epic Energy Services <epic.energy.services@administration.com>" : recipient.emailMittente())
                .queryString("to", recipient.emailDestinatario()) // <-- DEVE ESSERE IL DESTINATARIO VERIFICATO!
                .queryString("subject", recipient.oggetto() == null || recipient.oggetto().isEmpty() ? "" : recipient.oggetto())
                .queryString("text", recipient.testo())
                .asJson();
        System.out.println(response.getBody());
        return response.getBody().toPrettyString();
    }
}
