package restaurant.service.card;

import org.springframework.stereotype.Service;
import restaurant.model.Card;
import restaurant.model.User;
import restaurant.model.validation.Notification;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CardService {
    List<Card> findByClientName(String name);

    Notification<Boolean> addCard(String accNr, LocalDate idate, LocalDate edate, float sum, boolean credit, User client);
}
