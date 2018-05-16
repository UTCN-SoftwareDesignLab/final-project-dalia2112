package restaurant.model.validation;

import restaurant.model.Card;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardValidator {

    private final int CARD_NR_LENGTH = 5;
    private final Card card;
    private List<String> errors;

    public CardValidator(Card card) {
        this.card = card;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateMoney(card.getSum());
        validateCardNr(card.getAccountNumber());
        validateDates(card.getIssueDate(), card.getExpiryDate());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateMoney(float money) {
        if (money < 0.0)
            errors.add("Not enough money!");
    }

    private void validateCardNr(String nr) {
        if (nr.length() != 5)
            errors.add("Invalid account number!");
    }

    private void validateDates(LocalDate d1, LocalDate d2) {
        if (d1.isAfter(LocalDate.now()))
            errors.add("Invalid issue date!");
        if (d2.isBefore(LocalDate.now()))
            errors.add("Invalid exipiry date!");
    }
}
