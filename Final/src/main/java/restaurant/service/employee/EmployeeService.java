package restaurant.service.employee;

import org.springframework.stereotype.Service;
import restaurant.model.Orderr;
import restaurant.model.validation.Notification;

@Service
public interface EmployeeService {
    Notification<Boolean> takeOrderr(Orderr orderr);
}
