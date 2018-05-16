package restaurant.model.validation;

import restaurant.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private List<String> ROLES = Arrays.asList("admin", "employee", "client");

    private final User user;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    public UserValidator(User user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public void setUserExists() {
        errors.add("User already exists!");
    }

    public UserValidator() {
        user = null;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateUsername(user.getName());
        validatePassword(user.getPassword());
        validateRole(user.getRole());
        return errors.isEmpty();
    }

    private void validateUsername(String username) {
        if (!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()) {
            errors.add("Invalid email!");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password too short!");
        }
        if (!containsSpecialCharacter(password)) {
            errors.add("Password must contain at least one special character!");
        }
        if (!containsDigit(password)) {
            errors.add("Password must contain at least one number!");
        }
    }

    private void validateRole(String role) {
        if (!ROLES.contains(role))
            errors.add("Invalid Role!");
    }

    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private static boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateUpdate(String username, String password, String role) {

        validateUsername(username);
        validatePassword(password);
        if (!ROLES.contains(role))
            errors.add("Invalid role!");

        return errors.isEmpty();

    }

    public String getFormattedErrors() {
        String result = "";
        for (String error : getErrors())
            result += error + "\n \n";
        return result;
    }
}
