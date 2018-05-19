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

    Card findById(long id);

    Notification<Boolean> addCard(String accNr, int month,int year, float sum,int cvv, boolean credit, User client);

    Notification<Boolean> checkandPay(User user,String credCardNr,int expMonth,int expYear,int cvv,float sumToPay);

}
