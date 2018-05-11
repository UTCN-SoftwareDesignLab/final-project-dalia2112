package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
