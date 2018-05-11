package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Orderr;

public interface OrderrRepository extends JpaRepository<Orderr, Long> {
}
