package restaurant.service.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Card;
import restaurant.model.User;
import restaurant.model.builder.CardBuilder;
import restaurant.model.validation.CardValidator;
import restaurant.model.validation.Notification;
import restaurant.repository.CardRepository;

import java.time.LocalDate;
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
    public Notification<Boolean> addCard(String accNr, LocalDate idate, LocalDate edate, float sum, boolean credit, User client) {
        Notification<Boolean> notification = new Notification<>();
        Card card = new CardBuilder()
                .setClient(client)
                .setExpiryDate(edate)
                .setIssueDate(idate)
                .setName(accNr)
                .setSum(sum)
                .setCredit(credit)
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
}
