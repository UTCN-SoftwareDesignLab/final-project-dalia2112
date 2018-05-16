package restaurant.service.employee;

import org.springframework.stereotype.Service;
import restaurant.model.Orderr;
import restaurant.model.validation.Notification;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public Notification<Boolean> takeOrderr(Orderr orderr) {
        return null;
    }
}
