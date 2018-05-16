package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findById(long id);

    Dish findByName(String name);
}
