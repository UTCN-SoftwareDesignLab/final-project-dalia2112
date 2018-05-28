package restaurant.service.mail;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    void sendNotification(String email);
}
