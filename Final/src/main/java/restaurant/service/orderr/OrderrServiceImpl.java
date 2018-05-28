package restaurant.service.orderr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.builder.OrderrBuilder;
import restaurant.model.validation.Notification;
import restaurant.repository.OrderrRepository;

import java.util.*;

@Service
public class OrderrServiceImpl implements OrderrService {
    @Autowired
    private OrderrRepository orderrRepository;

    public OrderrServiceImpl(OrderrRepository orderrRepository) {
        this.orderrRepository = orderrRepository;
    }

    @Override
    public List<Orderr> findAll() {
        return orderrRepository.findAll();
    }

    private Orderr getUnprocessedOrderrs(List<Orderr> orderrs) {
        for (Orderr orderr : orderrs) {
            if (!orderr.isProcessed())
                return orderr;
        }
        return null;
    }

    @Override
    public Notification<Boolean> addOrderr(Map<Dish, Integer> dishMap, User client) {
        Notification<Boolean> notification = new Notification<>();
        List<Orderr> orderr1 = orderrRepository.findByClientId(client.getId());
        float sum = 0;
        for (Dish dish1 : dishMap.keySet()) {
            sum += dish1.getPrice() * dishMap.get(dish1);
        }
        if (getUnprocessedOrderrs(orderr1) == null) {
            Orderr orderr = new OrderrBuilder()
                    .setDishes(dishMap)
                    .setClient(client)
                    .setReceit(sum)
                    .setProcessed(false)
                    .setRating(0)
                    .setCar(0)
                    .build();
            notification.setResult(true);
            orderrRepository.save(orderr);
        } else { //the order exists and we add dishes to it
            Orderr orderr = getUnprocessedOrderrs(orderr1);
            Map tmp = new HashMap(dishMap);
            tmp.keySet().removeAll(orderr.getDishes().keySet()); //get all new dished added
            orderr.getDishes().putAll(tmp);
            orderr.setReceit(orderr.getReceit() + sum);
            notification.setResult(true);
            orderrRepository.save(orderr);
        }
        return notification;
    }

    @Override
    public List<String> cartDishes(User user) {
        if (orderrRepository.findByClientId(user.getId()).size() == 0) return null;
        Orderr orderr = getUnprocessedOrderrs(orderrRepository.findByClientId(user.getId()));
        if (orderr == null) return null;
        //This hashmap represents the dishes and their quantities in an order
        Map<Dish, Integer> orderDishes = getUnprocessedOrderrs(orderrRepository.findByClientId(user.getId())).getDishes();
        List<String> list = new ArrayList<>();
        for (Dish dish : orderDishes.keySet()) {
            float subprice = dish.getPrice() * orderDishes.get(dish);
            if (!list.contains(dish.getName() + "\t        x " + orderDishes.get(dish) + "\t         " + subprice)) {
                list.add(dish.getName() + "\t        x " + orderDishes.get(dish) + "\t         " + subprice);
            }
        }
        return list;
    }


    @Override
    public Orderr findByClientId(long id) {
        if (orderrRepository.findByClientId(id).size() == 0) return null;
        return getUnprocessedOrderrs(orderrRepository.findByClientId(id));
    }


    @Override
    public void payOrderr(Orderr orderr, double distance) {
        orderr.setProcessed(true);
        orderr.setDistance(distance);
        orderrRepository.save(orderr);
    }

    private Orderr getLastProcessedOrderr(List<Orderr> orderrs) {
        for (int counter = orderrs.size() - 1; counter >= 0; counter--) {
            if (orderrs.get(counter).isProcessed())
                return orderrs.get(counter);
        }
        return null;
    }

    public Notification<Boolean> setRating(String star, long userId) {
        Notification<Boolean> notification = new Notification<>();
        int starInt = new Scanner(star).useDelimiter("\\D+").nextInt();
        Orderr orderr = getLastProcessedOrderr(orderrRepository.findByClientId(userId));
        if (orderr == null) {
            notification.addError("You must pay the order first and then you can rate us!");
            notification.setResult(false);
            return notification;
        }
        notification.setResult(true);
        orderr.setRating(starInt);
        orderrRepository.save(orderr);
        return notification;
    }

    @Override
    public Orderr findById(long id) {
        return orderrRepository.findById(id);
    }

    public void delete(Orderr orderr) {
        orderrRepository.delete(orderr);
    }

}
