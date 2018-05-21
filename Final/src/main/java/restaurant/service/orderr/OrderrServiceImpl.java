package restaurant.service.orderr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.builder.OrderrBuilder;
import restaurant.model.validation.Notification;
import restaurant.repository.OrderrRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderrServiceImpl implements OrderrService {
    @Autowired
    private OrderrRepository orderrRepository;


    @Override
    public List<Orderr> findAll() {
        return orderrRepository.findAll();
    }

    public Orderr getUnprocessed(List<Orderr> orderrs) {
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
        List<Dish> dishes = new ArrayList<>();
        dishes.addAll(dishMap.keySet());
        if (getUnprocessed(orderr1) == null) {
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
        } else {

            Orderr orderr = getUnprocessed(orderr1);
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
        Orderr orderr = getUnprocessed(orderrRepository.findByClientId(user.getId()));
        if (orderr == null) return null;
        Map<Dish, Integer> dishist = getUnprocessed(orderrRepository.findByClientId(user.getId())).getDishes();
        List<String> list = new ArrayList<>();
        for (Dish dish : dishist.keySet()) {
            float subprice = dish.getPrice() * dishist.get(dish);
            if (!list.contains(dish.getName() + "\t        x " + dishist.get(dish) + "\t         " + subprice)) {
                list.add(dish.getName() + "\t        x " + dishist.get(dish) + "\t         " + subprice);
            }
        }
        return list;
    }


    @Override
    public Orderr findByClientId(long id) {
        if (orderrRepository.findByClientId(id).size() == 0) return null;
        return getUnprocessed(orderrRepository.findByClientId(id));
    }

    @Override
    public Notification<Boolean> completeOrderr(long userId, String addr, String city, String state, int zip) {
        Notification<Boolean> notification = new Notification<>();
        if (orderrRepository.findByClientId(userId).size() == 0) {
            notification.addError("No order!");
            notification.setResult(false);
            return notification;
        }
        Orderr orderr = getUnprocessed(orderrRepository.findByClientId(userId));
        orderr.setAddress(addr);
        orderr.setCity(city);
        orderr.setState(state);
        orderr.setZip(zip);
        notification.setResult(true);
        return notification;
    }

    @Override
    public void payOrderr(Orderr orderr) {
        orderr.setProcessed(true);
        orderrRepository.save(orderr);
    }

    private Orderr getLastProcessedOrderr(List<Orderr> orderrs) {
        for (int counter = orderrs.size() - 1; counter >= 0; counter--) {
            if (orderrs.get(counter).isProcessed())
                return orderrs.get(counter);
        }
        return null;
    }

    public void setRating(String star, long userId) {
        int starInt = Integer.parseInt(star.substring(4, 5));
        Orderr orderr = getLastProcessedOrderr(orderrRepository.findByClientId(userId));
        orderr.setRating(starInt);
        orderrRepository.save(orderr);
    }

//    public List<Orderr> getAllProcessedOrders(){
//        List<Orderr> orderrs=new ArrayList<>();
//        for(Orderr orderr:orderrRepository.findAll()){
//            if(orderr.isProcessed())
//                orderrs.add(orderr);
//        }
//        return orderrs;
//    }

    @Override
    public  Orderr findById(long id){
        return orderrRepository.findById(id);
    }

//    public List<Orderr> getDeliveredOrders() {
//        List<Orderr> orderrs = new ArrayList<>();
//        for (Orderr orderr : getAllProcessedOrders()) {
//            if (orderr.getCar() != 0)
//                orderrs.add(orderr);
//
//        }
//        return orderrs;
//    }
}
