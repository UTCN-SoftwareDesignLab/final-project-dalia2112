package restaurant.model.validation;

import restaurant.model.Card;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CardValidator {

    private final int CCV_LENGTH = 3;
    private final int MONTH_MIN = 1;
    private final int MONTH_MAX = 12;
    private final String ACC_NR_REGEX = "[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]$";
    private final Card card;
    private List<String> errors;

    public CardValidator(Card card) {
        this.card = card;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateMoney(card.getSum());
        validateDate(card.getExpMonth(), card.getExpYear());
        validateAccNr(card.getAccountNumber());
        validateCVV(card.getcVV());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateAccNr(String accnr) {
        if (!Pattern.compile(ACC_NR_REGEX).matcher(accnr).matches()) {
            errors.add("Invalid card number!");
        }
    }

    private void validateCVV(int cvv) {
        if (String.valueOf(cvv).length()!=CCV_LENGTH) {
            errors.add("Invalid CVV");
        }
    }

    private void validateMoney(float money) {
        if (money < 0.0)
            errors.add("Not enough money!");
    }

    private void validateDate(int month, int year) {
        if(month<MONTH_MIN || month>MONTH_MAX){
            errors.add("Invalid month!");
        }
        if (year < LocalDate.now().getYear())
            errors.add("Invalid expiry date!");
        else if (year == LocalDate.now().getYear())
            if (month < LocalDate.now().getMonth().getValue())
                errors.add("Invalid exipiry date!");
    }

    public String getFormattedErrors() {
        String result = "";
        for (String error : getErrors())
            result += error + "\n \n";
        return result;
    }
}
