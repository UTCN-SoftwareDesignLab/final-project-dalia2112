package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.model.Card;
import restaurant.model.Dish;
import restaurant.model.User;
import restaurant.model.validation.Notification;
import restaurant.service.card.CardService;
import restaurant.service.dish.DishService;
import restaurant.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String showCardPage() {
        return "client";
    }

    @RequestMapping(value = "/clientPage", method = RequestMethod.GET)
    public String showClientMenu() {
        return "clientPage";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String showOrderPage(Model model) {
        List<Dish> dishes = dishService.findAll();
        model.addAttribute("dishes", dishes);
        return "orders";
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public String showDeliveryMenu() {
        return "delivery";
    }


    // ADD CARD TO CLIENT
    @RequestMapping(value = "/client", params = "addCard", method = RequestMethod.POST)
    public String addClient(Authentication authentication, HttpServletRequest request, Model model, @RequestParam String idate, @RequestParam String edate, @RequestParam String accnr, @RequestParam float sum) {
        String credit = request.getParameter("mySelectos").toString();
        boolean creditBool = false;
        if (credit.equalsIgnoreCase("credit")) creditBool = true;
        User client = userService.findByUsername(authentication.getName());

        LocalDate d1 = LocalDate.parse(idate);
        LocalDate d2 = LocalDate.parse(edate);

        Notification<Boolean> notification = cardService.addCard(accnr, d1, d2, sum, creditBool, client);
        if (notification.getResult()) {
            model.addAttribute("updUSucc", true);
            model.addAttribute("updMessage2", "Card added successfully!");
            return "client";
        }
        model.addAttribute("updUErr", true);
        model.addAttribute("updMessage", notification.getFormattedErrors());
        return "client";
    }

    //VIEW CARDS
    @RequestMapping(value = "/cardView", params = "viewCards", method = RequestMethod.GET)
    public String readCards(Authentication authentication, Model model) {
        List<Card> items = cardService.findByClientName(authentication.getName());
        model.addAttribute("cards", items);
        return "client";
    }

    //VIEW DISHES

}
