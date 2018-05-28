package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.Greeting;
import restaurant.model.Card;
import restaurant.model.Dish;
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.validation.Notification;
import restaurant.service.card.CardService;
import restaurant.service.dish.DishService;
import restaurant.service.employee.EmployeeService;
import restaurant.service.orderr.OrderrService;
import restaurant.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private DishService dishService;
    @Autowired
    private OrderrService orderrService;
    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String showCardPage(Model model, Authentication authentication) {
        List<Card> items = cardService.findByClientName(authentication.getName());
        model.addAttribute("cards", items);
        return "client";
    }

    @RequestMapping(value = "/clientPage", method = RequestMethod.GET)
    public String showClientMenu(Authentication authentication) {
        return "clientPage";
    }


    // ADD CARD TO CLIENT
    @RequestMapping(value = "/client", params = "addCard", method = RequestMethod.POST)
    public String addClient(Authentication authentication, HttpServletRequest request, Model model, @RequestParam int month, @RequestParam int year, @RequestParam String accnr, @RequestParam int cvv, @RequestParam float sum) {
        String credit = request.getParameter("mySelectos").toString();
        boolean creditBool = false;
        if (credit.equalsIgnoreCase("credit")) creditBool = true;
        User client = userService.findByUsername(authentication.getName());
        Notification<Boolean> notification = cardService.addCard(accnr, month, year, sum, cvv, creditBool, client);
        if (notification.hasErrors())
            model.addAttribute("fMessage", notification.getFormattedErrors());
        else {
            model.addAttribute("sMessage", "Card added successfully!");
        }
        model.addAttribute("cards", cardService.findByClientName(authentication.getName()));
        return "client";
    }

    /*********************ORDERS*****************/
    //SHOW ORDERS PAGE
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String showOrderPage(Model model, Authentication authentication) {
        if (dishService.findAll().size() == 0) {
            dishService.createDishes();
            System.out.println("nu era");
        } else System.out.println("era ceva");
        User user = userService.findByUsername(authentication.getName());
        Orderr orderr = orderrService.findByClientId(user.getId());
        showDishes(model, dishService.findAll(), orderrService.cartDishes(user), orderr);
        return "orders";
    }


    //ADD DISHES TO ORDER
    @RequestMapping(value = "/orders", params = "cbb", method = RequestMethod.POST)
    public String commandDishes(Model model, HttpServletRequest request, Authentication authentication) {
        String[] dishesIds = request.getParameterValues("cbx");
        if (dishesIds.length == 0) {
            return "orders";
        }
        Map<Dish, Integer> dishNr = new HashMap<>();
        for (int i = 0; i < dishesIds.length; i++) {
            String quan = request.getParameter(dishesIds[i]);
            dishNr.put(dishService.findById(Long.parseLong(dishesIds[i])), Integer.parseInt(quan));
        }
        User user = userService.findByUsername(authentication.getName());
        orderrService.addOrderr(dishNr, user);

        Orderr orderr = orderrService.findByClientId(user.getId());
        showDishes(model, dishService.findAll(), orderrService.cartDishes(user), orderr);
        return "orders";
    }

    private void showDishes(Model model, List<Dish> dishes, List<String> cartDishes, Orderr orderr) {
        model.addAttribute("dishes", dishes);
        model.addAttribute("prodlst", cartDishes);
        if (orderr == null) model.addAttribute("totalPrice", 0);
        else
            model.addAttribute("totalPrice", orderr.getReceit());
    }

    //plata propriu-zisa
    @RequestMapping(value = "/orders", params = "chk", method = RequestMethod.POST)
    public String paypayOrder(Model model, Authentication authentication, @RequestParam String ccnum, @RequestParam int expmonth, @RequestParam int expyear, @RequestParam int cvv, @RequestParam(value = "ltt") String coordinates) {
        User user = userService.findByUsername(authentication.getName());
        if (orderrService.findByClientId(user.getId()) == null) {
            model.addAttribute("failMessage", "There is no unprocessed order to pay!");
            return "orders";
        }
        if (coordinates.equalsIgnoreCase("")) {
            model.addAttribute("failMessage", "You must give give your address by clicking on the map below!");
            return "orders";
        }
        Orderr orderr = orderrService.findByClientId(user.getId());
        Notification<Boolean> checkCard = cardService.checkandPay(user, ccnum, expmonth, expyear, cvv, orderr.getReceit());
        if (checkCard.hasErrors()) {
            model.addAttribute("failMessage", checkCard.getFormattedErrors());
            return "orders";
        }
        model.addAttribute("distance", orderr.getDistance());
        model.addAttribute("ordId", orderr.getId());
        double distance = employeeService.getDistanceFromRestaurant(Double.parseDouble(coordinates.split(" ")[0]), Double.parseDouble(coordinates.split(" ")[1]));
        orderrService.payOrderr(orderr, distance);
        showDishes(model, dishService.findAll(), null, null);
        model.addAttribute("succMessage", "Order payed. You will get news soon!");
        return "orders";
    }

    /*****REVIEWS***/
    @RequestMapping(value = "/orders", params = "rew", method = RequestMethod.POST)
    public String getReview(Model model, HttpServletRequest request, Authentication authentication) {
        String[] stars = request.getParameterValues("star");
        Notification<Boolean> notification = orderrService.setRating(stars[0], userService.findByUsername(authentication.getName()).getId());
        if (notification.hasErrors()) {
            model.addAttribute("failMessage", notification.getFormattedErrors());
        } else
            model.addAttribute("succMessage", "Thank you for your review! Your opinion matters to us!");
        User client = userService.findByUsername(authentication.getName());
        showDishes(model, dishService.findAll(), orderrService.cartDishes(client), orderrService.findByClientId(client.getId()));
        return "orders";
    }

    @MessageMapping("/hi")
    @SendTo("/topic/greetings")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Greeting sendOrderToEmployee(String message) {
        message = message.replace("{", "");
        message = message.replace("}", "");
        return new Greeting(message);
    }
}
