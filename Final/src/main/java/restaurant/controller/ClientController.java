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
import restaurant.model.Orderr;
import restaurant.model.User;
import restaurant.model.validation.Notification;
import restaurant.service.card.CardService;
import restaurant.service.dish.DishService;
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


    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String showCardPage() {
        return "client";
    }

    @RequestMapping(value = "/clientPage", method = RequestMethod.GET)
    public String showClientMenu() {
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
        return showErrs(model, notification.getResult(), "Card added successfully!", notification.getFormattedErrors());
    }

    private String showErrs(Model model, boolean err, String succMessage, String errMessage) {
        if (err) {
            model.addAttribute("updUSucc", true);
            model.addAttribute("updMessage2", succMessage);
            return "client";
        }
        model.addAttribute("updUErr", true);
        model.addAttribute("updMessage", errMessage);
        return "client";
    }

    //VIEW CARDS
    @RequestMapping(value = "/cardView", params = "viewCards", method = RequestMethod.GET)
    public String readCards(Authentication authentication, Model model) {
        List<Card> items = cardService.findByClientName(authentication.getName());
        model.addAttribute("cards", items);
        return "client";
    }


    /*********************ORDERS*****************/
    //SHOW ORDERS PAGE
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String showOrderPage(Model model, Authentication authentication) {
        if (dishService.findAll().size() == 0)
            dishService.createDishes();
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
        Notification<Boolean> notification = orderrService.addOrderr(dishNr, user);

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
    public String paypayOrder(Model model, HttpServletRequest request, Authentication authentication, @RequestParam String ccnum, @RequestParam int expmonth, @RequestParam int expyear, @RequestParam int cvv, @RequestParam(value = "ltt") String coordinates) {
        if (coordinates.equalsIgnoreCase("")) {
            showMessage(model, true, "", "You must give give your address by clicking on the map below!");
            return "orders";
        }

        User user = userService.findByUsername(authentication.getName());
        Orderr orderr = orderrService.findByClientId(user.getId());
        Notification<Boolean> checkCard = cardService.checkandPay(user, ccnum, expmonth, expyear, cvv, orderr.getReceit());
        if (checkCard.hasErrors()) {
            showMessage(model, true, "", checkCard.getFormattedErrors());
            return "orders";
        }
        orderrService.payOrderr(orderr, Double.parseDouble(coordinates.split(" ")[0]), Double.parseDouble(coordinates.split(" ")[1]));
        showDishes(model, dishService.findAll(), null, null);
        showMessage(model, false, "ALL done", "");

        System.out.println("Lat " + coordinates.split(" ")[0] + " Long " + coordinates.split(" ")[1]);
        return "orders";
    }

    private void showMessage(Model model, boolean err, String succMessage, String failMessage) {
        if (err) {
            model.addAttribute("fail", true);
            model.addAttribute("failMessage", failMessage);
        } else {
            model.addAttribute("succ", true);
            model.addAttribute("succMessage", succMessage);
        }
    }

    /*****REVIEWS***/
    //GET REVIEW
    @RequestMapping(value = "/orders", params = "rew", method = RequestMethod.POST)
    public String getReview(Model model, HttpServletRequest request, Authentication authentication) {
        String[] stars = request.getParameterValues("star");
        orderrService.setRating(stars[0], userService.findByUsername(authentication.getName()).getId());
        model.addAttribute("succ", true);
        model.addAttribute("succMessage", "Thank you for your review! Your opinion matters to us!");
        return "orders";
    }


}
