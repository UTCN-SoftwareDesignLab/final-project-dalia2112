package restaurant.model.builder;

import restaurant.model.Dish;
import restaurant.model.Ingredient;

import java.util.List;

public class DishBuilder {

    private Dish dish;

    public DishBuilder() {
        dish = new Dish();
    }

    public DishBuilder setId(long id) {
        dish.setId(id);
        return this;
    }

    public DishBuilder setName(String name) {
        dish.setName(name);
        return this;
    }

    public DishBuilder setMoney(float money) {
        dish.setPrice(money);
        return this;
    }

    public DishBuilder setIngredients(List<Ingredient> ingredients) {
        dish.setIngredients(ingredients);
        return this;
    }


    public Dish build() {
        return dish;
    }

}
