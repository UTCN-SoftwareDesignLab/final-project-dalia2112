package restaurant.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Orderr;

import java.util.List;

public interface OrderrRepository extends JpaRepository<Orderr, Long> {

    List<Orderr> findByClientId(long id);
}
