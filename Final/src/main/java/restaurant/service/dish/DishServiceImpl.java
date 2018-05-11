package restaurant.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.builder.DishBuilder;
import restaurant.repository.DishRepository;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public void addDish(String name, List<Ingredient> ingredients, float price, boolean availableOnline) {
        Dish dish=new DishBuilder()
                .setName(name)
                .setIngredients(ingredients)
                .setMoney(price)
                .setOnline(availableOnline)
                .build();
        dishRepository.save(dish);
    }
}
