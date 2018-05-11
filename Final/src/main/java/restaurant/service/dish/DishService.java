package restaurant.service.dish;

import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Ingredient;

import java.util.List;

@Service
public interface DishService {
    List<Dish> findAll();
    void addDish(String name, List<Ingredient> ingredients,float price,boolean availableOnline);
}
