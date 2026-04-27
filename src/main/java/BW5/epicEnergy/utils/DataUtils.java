package BW5.epicEnergy.utils;

public class DataUtils {
    public static String normalize(String input) {
        if (input == null) return "";
        return input.trim();//.toLowerCase();
        // Regex: remove everything that is NOT a-z or 0-9
        // .replaceAll("[^a-z0-9]", "");

    }
}
