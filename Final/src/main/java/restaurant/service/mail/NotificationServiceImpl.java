package restaurant.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;
//    public NotificationServiceImpl(JavaMailSender javaMailSender){
//        this.javaMailSender=javaMailSender;
//    }
    @Override
    public void sendNotification(String email) throws MailException {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("myfakey2018@gmail.com");
        mail.setSubject("Sa vedem daca merge - Subject");
        mail.setText("Fucking merge!");

        javaMailSender.send(mail);
    }
}
