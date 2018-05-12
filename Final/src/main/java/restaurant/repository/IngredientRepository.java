package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByName(String name);
}
