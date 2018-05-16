package restaurant.model.validation;

import restaurant.model.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishValidator {
    private final Dish dish;
    private List<String> errors;

    public DishValidator(Dish dish) {
        this.dish = dish;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validatePrice(dish.getPrice());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validatePrice(float money) {
        if (money < 0.0)
            errors.add("Not enough money!");
    }
}
