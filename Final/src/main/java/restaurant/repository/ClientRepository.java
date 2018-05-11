package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findById(long id);

    List<Client> findAll();

//    Client findByOrderrId(long id);
}
