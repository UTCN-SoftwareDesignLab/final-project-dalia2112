package restaurant.service.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Card;
import restaurant.model.User;
import restaurant.model.builder.CardBuilder;
import restaurant.model.validation.CardValidator;
import restaurant.model.validation.Notification;
import restaurant.repository.CardRepository;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;


    @Override
    public List<Card> findByClientName(String name) {
        return cardRepository.findByClientName(name);
    }

    @Override
    public Notification<Boolean> addCard(String accNr, int month, int year, float sum, int cvv, boolean credit, User client) {
        Notification<Boolean> notification = new Notification<>();
        Card card = new CardBuilder()
                .setClient(client)
                .setExpMonth(month)
                .setExpYear(year)
                .setName(accNr)
                .setSum(sum)
                .setCredit(credit)
                .setCVV(cvv)
                .build();
        CardValidator validator = new CardValidator(card);
        if (!validator.validate()) {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        } else {
            cardRepository.save(card);
            notification.setResult(true);
        }
        return notification;
    }

    @Override
    public Card findById(long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Notification<Boolean> checkandPay(User user, String credCardNr, int expMonth, int expYear, int cvv, float sumToPay) {
        Notification<Boolean> notification = new Notification<>();
        List<Card> cards = cardRepository.findByClientName(user.getName());
        if (cards.size() == 0) {
            return myNotification(notification, "This client has no cards!");
        }
        Card card = cardRepository.findByAccountNumber(credCardNr);
        if (card == null) {
            myNotification(notification, "Invalid credit card numeber!");
        }
        if (card.getExpMonth() == expMonth && card.getExpYear() == expYear && card.getcVV() == cvv) {
            if (sumToPay > card.getSum()) {
                if (!card.isCredit()) {
                    notification.addError("You don't have enough money!");
                    notification.setResult(false);
                    return notification;
                }
            }
            card.setSum(card.getSum() - sumToPay);
            cardRepository.save(card);
            notification.setResult(true);
            return notification;
        }
        return myNotification(notification, "One of exp month,year, or CVV is invalid!");
    }

    private Notification<Boolean> myNotification(Notification notification, String message) {
        notification.addError(message);
        notification.setResult(false);
        return notification;
    }
}
