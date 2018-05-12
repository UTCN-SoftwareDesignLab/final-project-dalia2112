package restaurant.service.dish;

import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Ingredient;

import java.util.List;

@Service
public interface DishService {
    List<Dish> findAll();
//    Dish findByName(String name);
    void addDish(String name, Ingredient ingredients,float price,boolean availableOnline);
    void updateDish(long id,String name,float price,boolean online);

    void deleteDish(long id);
}
