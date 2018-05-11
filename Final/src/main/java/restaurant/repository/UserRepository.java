package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.User;
import restaurant.model.validation.Notification;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findById(long id);

    List<User> findAll();

    User findByNameAndPassword(String username, String password);

    Notification<Boolean> deleteById(long id);

}