package restaurant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import restaurant.model.Card;
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.validation.Notification;
import restaurant.service.bill.BillService;
import restaurant.service.card.CardService;
import restaurant.service.employee.EmployeeService;
import restaurant.service.mail.NotificationServiceImpl;
import restaurant.service.orderr.OrderrService;
import restaurant.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrderrService orderrService;
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private BillService billService;

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public String showOrders(Model model, HttpServletRequest request) {
        System.out.println(orderrService.findAll().size());
        List<Orderr> orderrList = employeeService.getAllProcessedOrders();
        orderrList.removeAll(employeeService.getDeliveredOrders());
        model.addAttribute("orders", orderrList);
        model.addAttribute("orders2", employeeService.getDeliveredOrders());
        model.addAttribute("list", employeeService.getAvailableCars());
        return "delivery";
    }

    //set cars with orders => MAX 2 ORDERS/CAR
    @RequestMapping(value = "/delivery", params = "rew", method = RequestMethod.POST)
    public String setCars(Model model, HttpServletRequest request, Authentication authentication) {
        String[] orderIds = request.getParameterValues("cbx");
        if (orderIds == null || orderIds.length == 0) {
            return "delivery";
        }
        for (int i = 0; i < orderIds.length; i++) {
            Orderr orderr = orderrService.findById(Long.parseLong(orderIds[i]));
            Notification<Boolean> notification = employeeService.setCarToOrder(Integer.parseInt(request.getParameter(orderIds[i]).substring(4, 5)), orderr);
            if (!notification.getResult()) { // distance is too big => return money
                User client = userService.findById(orderr.getClient().getId());
                Card card = cardService.findByClientName(client.getName()).get(0);
                card.setSum(card.getSum() + orderr.getReceit());
                cardService.save(card);
                orderrService.delete(orderr);
                model.addAttribute("errMsg", notification.getFormattedErrors());
            }
            orderr.setEmployees(userService.findByUsername(authentication.getName()));
            billService.generateBill(orderr);
            model.addAttribute("succMsg", "The command has been delivered. Waiting time: " + employeeService.calcWaitingTime(orderr));
            List<Orderr> orderrList = employeeService.getAllProcessedOrders();
            orderrList.removeAll(employeeService.getDeliveredOrders());
            model.addAttribute("orders", orderrList);
            model.addAttribute("orders2", employeeService.getDeliveredOrders());
            model.addAttribute("list", employeeService.getAvailableCars());
        }
        return "delivery";
    }

    @RequestMapping(value = "/delivery", params = "sendMail", method = RequestMethod.POST)
    public String sendMail() {
        try {
            notificationService.sendNotification("daliacopaciu@gmail.com");
        } catch (MailException m) {
            m.printStackTrace();
            logger.info("Error mail " + m.getMessage());
        }
        return "delivery";
    }

//    private void sendEmail() throws Exception{
//        MimeMessage message = sender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setTo("daliacopaciu@gmail.com");
//        helper.setText("How are you?");
//        helper.setSubject("Hi");
//
//        sender.send(message);
//    }


}

