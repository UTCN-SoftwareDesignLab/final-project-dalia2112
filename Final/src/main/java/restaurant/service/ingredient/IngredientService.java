package restaurant.service.ingredient;

import org.springframework.stereotype.Service;
import restaurant.model.Ingredient;

import java.util.List;

@Service
public interface IngredientService {

    void createIngredients();

    List<Ingredient> findAll();

    List<Ingredient> findByName(String name);
}
