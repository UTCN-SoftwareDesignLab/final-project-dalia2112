package restaurant.service.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Constants;
import restaurant.model.Ingredient;
import restaurant.model.builder.IngredientBuilder;
import restaurant.repository.IngredientRepository;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public void createIngredients() {
        String[] vegan = Constants.Ingredients.VEGAN;
        String[] nonvegan = Constants.Ingredients.NONVEGAN;
        for (String veganIngr : vegan) {
            Ingredient ingredient = new IngredientBuilder()
                    .setName(veganIngr)
                    .setVegan(true)
                    .build();
            ingredientRepository.save(ingredient);
        }
        for (String nonveganIngr : nonvegan) {
            Ingredient ingredient = new IngredientBuilder()
                    .setName(nonveganIngr)
                    .setVegan(false)
                    .build();
            ingredientRepository.save(ingredient);
        }
    }

    public List<Ingredient> findAll(){
        return ingredientRepository.findAll();
    }
}
