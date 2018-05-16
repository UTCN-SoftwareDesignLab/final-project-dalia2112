package restaurant.service.orderr;

import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;

import java.util.List;

@Service
public interface OrderrService {
    List<Orderr> findAll();

    void addOrderr(List<Dish> dishes, User client, float receit, boolean online);
}
