package restaurant.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Orderr;
import restaurant.model.validation.Notification;
import restaurant.repository.OrderrRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private OrderrRepository orderrRepository;

    @Override
    public List<String> getCars() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add("Car " + i);
        }
        return list;
    }

    public List<String> getAvailableCars() {
        List<String> car = new ArrayList<>();
        for (String cars : getCars()) {
            List<Orderr> orderrs1 = orderrRepository.findByCar(Integer.parseInt(cars.substring(4, 5)));
            if (orderrs1.size() < 2)
                car.add(cars);
        }
        return car;
    }

    public Notification<Boolean> setCarToOrder(int car, Orderr orderr) {
        Notification<Boolean> notification = new Notification<>();
        if (getDistanceFromRestaurant(orderr) > 15) {
            notification.addError("Delivery address of order " + orderr.getId() + " is greater than 15 km! Your money will be returned.");
            notification.setResult(false);
            return notification;
        }
        List<Orderr> orderrs = orderrRepository.findByCar(car);
        if (orderrs.size() < 2) {
            orderr.setCar(car);
            orderrRepository.save(orderr);
            notification.setResult(true);
            return notification;
        }
        String crs = "";
        for (String car1 : getAvailableCars()) {
            crs += car1 + " ";
        }
        notification.addError("Car " + car + " NOT available. Available cars: " + crs);
        notification.setResult(false);
        return notification;
    }


    public List<Orderr> getDeliveredOrders() {
        List<Orderr> orderrs = new ArrayList<>();
        for (Orderr orderr : getAllProcessedOrders()) {
            if (orderr.getCar() != 0)
                orderrs.add(orderr);
        }
        return orderrs;
    }

    public List<Orderr> getAllProcessedOrders() {
        List<Orderr> orderrs = new ArrayList<>();
        for (Orderr orderr : orderrRepository.findAll()) {
            if (orderr.isProcessed())
                orderrs.add(orderr);
        }
        return orderrs;
    }

    private double getDistanceFromRestaurant(Orderr orderr) {
        double restaurantLatitude = -16.541324 * (Math.PI / 180);
        double restaurantLongitude = -151.734107 * (Math.PI / 180);
        double chosenLatitude = orderr.getCoordLat() * (Math.PI / 180);
        double chosenLongitude = orderr.getCoordLng() * (Math.PI / 180);
        double diffLat = (restaurantLatitude - chosenLatitude);
        double diffLng = (restaurantLongitude - chosenLongitude);
        double sinLat = Math.sin(diffLat / 2);
        double sinLng = Math.sin(diffLng / 2);
        double a = Math.pow(sinLat, 2.0) + Math.cos(restaurantLatitude) * Math.cos(chosenLatitude) * Math.pow(sinLng, 2.0);
        double earth_radius = 6378.1;
        double distance = earth_radius * 2 * Math.asin(Math.min(1, Math.sqrt(a)));
        return distance;
    }

}
