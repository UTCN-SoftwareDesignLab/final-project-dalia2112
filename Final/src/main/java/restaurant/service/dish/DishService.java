package restaurant.service.dish;

import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.validation.Notification;

import java.util.List;

@Service
public interface DishService {
    List<Dish> findAll();

    Dish findById(long id);

    Notification<Boolean> addDish(String name, Ingredient ingredients, float price);

    Notification<Boolean> updateDish(long id, String name, float price);

    Notification<Boolean> deleteDish(long id);
}
