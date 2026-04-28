package BW5.epicEnergy.exception;

import java.util.List;

public class ValidationExceptions extends RuntimeException {

    private List<String> errors;

    public ValidationExceptions(String message) {
        super(message);
    }

    public ValidationExceptions(List<String> errors) {
        super("Errori di validazione");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
