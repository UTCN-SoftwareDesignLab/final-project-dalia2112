package restaurant.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Constants;
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

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Dish findById(long id) {
        return dishRepository.findById(id);
    }

    public void createDishes() {
        for (int i = 0; i < 3; i++) {
            Dish dish = new DishBuilder()
                    .setName(Constants.Dishes.NAMES[i])
                    .setIngredients(Constants.Dishes.getIngredients().get(i))
                    .setMoney(Constants.Dishes.defaultDishesPrices[i])
                    .build();
            dishRepository.save(dish);
        }
    }

    @Override
    public Notification<Boolean> addDish(String name, Ingredient ingredient, float price) {

        Notification<Boolean> notification = new Notification<>();
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
            DishValidator validator=new DishValidator(dish);
            if(!validator.validate())
                notification.addError(validator.getFormattedErrors());
            else {
                notification.setResult(true);
                dishRepository.save(dish);
            }
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
