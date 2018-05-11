package restaurant.model.builder;

import restaurant.model.Ingredient;


public class IngredientBuilder {

    private Ingredient ingredient;

    public IngredientBuilder() {
        ingredient = new Ingredient();
    }

    public IngredientBuilder setId(long id) {
        ingredient.setId(id);
        return this;
    }

    public IngredientBuilder setName(String name) {
        ingredient.setName(name);
        return this;
    }

    public IngredientBuilder setVegan(boolean vegan) {
        ingredient.setVegan(vegan);
        return this;
    }


    public Ingredient build() {
        return ingredient;
    }
}
