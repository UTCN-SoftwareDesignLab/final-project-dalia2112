package restaurant.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.builder.DishBuilder;
import restaurant.model.validation.DishValidator;
import restaurant.model.validation.Notification;
import restaurant.repository.DishRepository;

import java.util.ArrayList;
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
    public Dish findById(long id){
        return dishRepository.findById(id);
    }

    @Override
    public Notification<Boolean> addDish(String name, Ingredient ingredient, float price) {

        Notification<Boolean> notification = new Notification<>();
//        DishValidator
        Dish dish = dishRepository.findByName(name);
        List<Ingredient> ingredients;
        if (dish == null) {
            ingredients = new ArrayList<>();
            ingredients.add(ingredient);
            System.out.println("era null");
            Dish dish1 = new DishBuilder()
                    .setName(name)
                    .setIngredients(ingredients)
                    .setMoney(price)
                    .build();
            DishValidator validator = new DishValidator(dish1);
            if (!validator.validate()) {
                validator.getErrors().forEach(notification::addError);
                notification.setResult(false);
            } else {
                dishRepository.save(dish1);
                notification.setResult(true);
            }
        } else {
            ingredients = dish.getIngredients();
            ingredients.add(ingredient);
            dish.setIngredients(ingredients);
            System.out.println("NU era null " + ingredients.get(0).getName());
            dishRepository.save(dish);
            notification.setResult(true);
        }
        return notification;

    }

    public Notification<Boolean> updateDish(long id, String name, float price) {
        Dish dish = dishRepository.findById(id);
        Notification<Boolean> notification = new Notification<>();
        if (dish != null) {
            dish.setName(name);
            dish.setPrice(price);
            notification.setResult(true);
            dishRepository.save(dish);
        } else {
            notification.addError("Dish does not exist!");
            notification.setResult(false);
        }
        return notification;
    }

    public Notification<Boolean> deleteDish(long id) {
        Notification<Boolean> notification = new Notification<>();
        if (dishRepository.findById(id) == null) {
            notification.addError("Dish does not exist!");
            notification.setResult(false);
        } else {
            notification.setResult(true);
            dishRepository.deleteById(id);
        }
        return notification;
    }
}
