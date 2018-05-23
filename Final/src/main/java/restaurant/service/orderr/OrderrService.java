package restaurant.service.orderr;

import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.validation.Notification;

import java.util.List;
import java.util.Map;

@Service
public interface OrderrService {
    List<Orderr> findAll();

    Notification<Boolean> addOrderr(Map<Dish, Integer> dishMap, User client);

    List<String> cartDishes(User user);

    Orderr findByClientId(long id);

    void payOrderr(Orderr orderr, double lat, double lng);

    void setRating(String star, long userId);

    Orderr findById(long id);

    void delete(Orderr orderr);
}
