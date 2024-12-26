package utils;

public class InputValidator {

    public static boolean validateUsername(String username) {
        return username != null;
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean validateAmount(double amount) {
        return amount > 0;
    }

    public static boolean validateCategory(String category) {
        return category != null && !category.trim().isEmpty();
    }
}
