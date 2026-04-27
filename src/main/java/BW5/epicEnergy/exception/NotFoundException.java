package BW5.epicEnergy.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resourceType) {
        super("The " + resourceType + " has not been found.");
    }
}