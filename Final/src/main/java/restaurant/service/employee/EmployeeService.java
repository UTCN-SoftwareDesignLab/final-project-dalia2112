package restaurant.service.employee;

import org.springframework.stereotype.Service;
import restaurant.model.Orderr;
import restaurant.model.validation.Notification;

import java.util.List;

@Service
public interface EmployeeService {
    List<String> getCars();

    Notification<Boolean> setCarToOrder(int car, Orderr orderr);

    List<String> getAvailableCars();

    List<Orderr> getDeliveredOrders();

    List<Orderr> getAllProcessedOrders();


}
