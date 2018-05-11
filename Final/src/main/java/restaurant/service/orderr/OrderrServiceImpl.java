package restaurant.service.orderr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Client;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.builder.OrderrBuilder;
import restaurant.repository.OrderrRepository;

import java.util.List;

@Service
public class OrderrServiceImpl implements OrderrService {
    @Autowired
    private OrderrRepository orderrRepository;


    @Override
    public List<Orderr> findAll() {
        return orderrRepository.findAll();
    }

    @Override
    public void addOrderr(List<Dish> dishes, Client client, float receit, boolean online) {
        Orderr orderr=new OrderrBuilder()
                .setDishes(dishes)
                .setClient(client)
                .setReceit(receit)
                .setOnline(online)
                .build();
        orderrRepository.save(orderr);
    }
}
