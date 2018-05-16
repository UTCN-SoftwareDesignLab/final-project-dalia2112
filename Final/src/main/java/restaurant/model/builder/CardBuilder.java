package restaurant.model.builder;

import restaurant.model.Card;
import restaurant.model.User;

import java.time.LocalDate;

public class CardBuilder {

    private Card card;

    public CardBuilder() {
        card = new Card();
    }

    public CardBuilder setId(long id) {
        card.setId(id);
        return this;
    }

    public CardBuilder setName(String accNr) {
        card.setAccountNumber(accNr);
        return this;
    }

    public CardBuilder setIssueDate(LocalDate date) {
        card.setIssueDate(date);
        return this;
    }

    public CardBuilder setExpiryDate(LocalDate date) {
        card.setExpiryDate(date);
        return this;
    }

    public CardBuilder setSum(float sum) {
        card.setSum(sum);
        return this;
    }

    public CardBuilder setClient(User client) {
        card.setClient(client);
        return this;
    }

    public CardBuilder setCredit(boolean credit) {
        card.setCredit(credit);
        return this;
    }


    public Card build() {
        return card;
    }
}
