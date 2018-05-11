package restaurant.service.orderr;

import org.springframework.stereotype.Service;
import restaurant.model.Client;
import restaurant.model.Dish;
import restaurant.model.Orderr;

import java.util.List;

@Service
public interface OrderrService {
    List<Orderr> findAll();

    void addOrderr(List<Dish> dishes, Client client, float receit, boolean online);
}
