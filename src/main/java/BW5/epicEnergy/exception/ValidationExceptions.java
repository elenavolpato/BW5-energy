package BW5.epicEnergy.exception;

import java.util.List;

public class ValidationExceptions extends RuntimeException {

    private List<String> errors;

    public ValidationExceptions(String message) {

    }
}
