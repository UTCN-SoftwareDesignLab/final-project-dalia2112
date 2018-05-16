package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByClientName(String name);
}
