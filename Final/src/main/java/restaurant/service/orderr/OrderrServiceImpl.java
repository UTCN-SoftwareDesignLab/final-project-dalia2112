package restaurant.service.orderr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Card;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.builder.OrderrBuilder;
import restaurant.model.validation.Notification;
import restaurant.repository.OrderrRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class OrderrServiceImpl implements OrderrService {
    @Autowired
    private OrderrRepository orderrRepository;


    @Override
    public List<Orderr> findAll() {
        return orderrRepository.findAll();
    }

    @Override
    public Notification<Boolean> addOrderr(Map<Dish, Integer> dishMap, User client) {
        Notification<Boolean> notification = new Notification<>();
        List<Orderr> orderr1 = orderrRepository.findByClientId(client.getId());
        float sum = 0;
        for (Dish dish1 : dishMap.keySet()) {
            sum += dish1.getPrice() * dishMap.get(dish1);
        }
        List<Dish> dishes = new ArrayList<>();
        dishes.addAll(dishMap.keySet());
        if (orderr1.size() == 0) {
            Orderr orderr = new OrderrBuilder()
                    .setDishes(dishMap)
                    .setClient(client)
                    .setReceit(sum)
                    .build();
            notification.setResult(true);
            orderrRepository.save(orderr);
        } else {


            Orderr orderr = orderr1.get(0);
            Map tmp = new HashMap(dishMap);
            tmp.keySet().removeAll(orderr.getDishes().keySet());
            orderr.getDishes().putAll(tmp);

            orderr.setReceit(orderr.getReceit() + sum);
//            orderr.getDishes().
            notification.setResult(true);
            orderrRepository.save(orderr);
        }
        return notification;
    }

    @Override
    public List<String> cartDishes(User user) {
        if (orderrRepository.findByClientId(user.getId()).size() == 0) return null;

        Map<Dish,Integer> dishist=orderrRepository.findByClientId(user.getId()).get(0).getDishes();
        List<String> list = new ArrayList<>();
        for (Dish dish : dishist.keySet()) {
            float subprice = dish.getPrice() * dishist.get(dish);
            if (!list.contains(dish.getName() + "\t        x " + dishist.get(dish) + "\t         " + subprice)) {
                list.add(dish.getName() + "\t        x " + dishist.get(dish) + "\t         " + subprice);
            }
        }
        System.out.println(list.size());
        return list;
    }


    @Override
    public Orderr findByClientId(long id) {
        if (orderrRepository.findByClientId(id).size() == 0) return null;
        return orderrRepository.findByClientId(id).get(0);
    }

    @Override
    public Notification<Boolean> completeOrderr(long userId, String addr, String city, String state, int zip) {
        Notification<Boolean> notification = new Notification<>();
        if(orderrRepository.findByClientId(userId).size()==0){
            notification.addError("No order!");
            notification.setResult(false);
            return notification;
        }
        Orderr orderr = orderrRepository.findByClientId(userId).get(0);
        orderr.setAddress(addr);
        orderr.setCity(city);
        orderr.setState(state);
        orderr.setZip(zip);
        notification.setResult(true);
        return notification;
    }

    @Override
    public void payOrderr(long orderrId){
        orderrRepository.deleteById(orderrId);
    }

}
