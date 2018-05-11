package restaurant.service.user;

import org.springframework.stereotype.Service;
import restaurant.model.User;
import restaurant.model.validation.Notification;

import java.util.List;

@Service
public interface UserService {

    List<User> findAll();

    Notification<Boolean> deleteUser(long id);

    User findById(long id);

    User findByUsername(String username);

    Notification<Boolean> registerUser(String username, String password, String role);

    Notification<Boolean> login(String username, String password);

    Notification<Boolean> updateUser(long id, String name, String password, String role);


}
