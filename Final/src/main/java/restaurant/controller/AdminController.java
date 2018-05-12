package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.model.Constants;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.User;
import restaurant.model.validation.Notification;
import restaurant.service.dish.DishService;
import restaurant.service.ingredient.IngredientService;
import restaurant.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    @Autowired
    private IngredientService ingredientService;

    /***************************USER *************************************/

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser() {
        return "user";
    }

    //READ/LIST USER
    @RequestMapping(value = "/userView", params = "viewUsers", method = RequestMethod.GET)
    public String readUsers(Model model) {
        List<User> items = userService.findAll();
        model.addAttribute("users", items);
        return "user";
    }

    //UPDATE USER
    @RequestMapping(value = "/user", params = "update", method = RequestMethod.GET)
    public String updateUser(Model model, @RequestParam long id, @RequestParam String username, @RequestParam String password, @RequestParam String role) {

        Notification<Boolean> notification = userService.updateUser(id, username, password, role);
        if (!notification.getResult()) {
            model.addAttribute("updUErr", true);
            model.addAttribute("updMessage", notification.getFormattedErrors());
            return "user";
        }

        model.addAttribute("updUSucc", true);
        model.addAttribute("updMessage2", "User updated successfully!");
        return "user";

    }


    //DELETE USER
    @RequestMapping(value = "/user", params = "dlt", method = RequestMethod.POST)
    public String deleteUser(Model model, @RequestParam long id) {

        Notification<Boolean> notification = userService.deleteUser(id);
        if (!notification.getResult()) {
            model.addAttribute("delUErr", true);
            model.addAttribute("delMessage", notification.getFormattedErrors());
            return "user";
        }
        model.addAttribute("delSucc", true);
        return "user";
    }


    @RequestMapping(value = "/logout", params = "logout", method = RequestMethod.GET)
    public String logout() {
        return "redirect:/login";
    }


    /****************  MENU  ****************/

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String showMenu(Model model,HttpServletRequest request) {
        return "menu";
    }

    //ADD DISH
    @RequestMapping(value = "/menu",params ="ps",method = RequestMethod.POST)
    public String addDish(HttpServletRequest request,@RequestParam String name,@RequestParam float price,@RequestParam String online) {

        boolean ok;
        if(online.equalsIgnoreCase("yes")) ok=true;
        else ok=false;
        System.out.println("aci");
        Ingredient ingredient=ingredientService.findByName(request.getParameter("mySelect2")).get(0);
        System.out.println("aci2");
        dishService.addDish(name,ingredient,price,ok);
        System.out.println(request.getParameter("mySelect2"));
        return "menu";
    }

    //VIEW DISHES
    @RequestMapping(value = "/dishesView", params = "viewDishes", method = RequestMethod.GET)
    public String viewDishes(Model model) {
        List<Dish> items = dishService.findAll();
//        model.addAttribute("listaingr",)
        model.addAttribute("dishes", items);
        return "/menu";
    }

    //UPDATE DISH
    @RequestMapping(value = "/menu",params ="update",method = RequestMethod.POST)
    public String updateDish(@RequestParam long id,@RequestParam String name,@RequestParam float price,@RequestParam String online) {

        boolean ok;
        if(online.equalsIgnoreCase("yes")) ok=true;
        else ok=false;
        System.out.println("aci");
        dishService.updateDish(id,name,price,ok);
        return "menu";
    }

    //DELETE DISH
    @RequestMapping(value = "/menu", params = "dlt", method = RequestMethod.POST)
    public String deleteDish(Model model, @RequestParam long id) {

        dishService.deleteDish(id);

//        Notification<Boolean> notification = userService.deleteUser(id);
//        if (!notification.getResult()) {
//            model.addAttribute("updUErr", true);
//            model.addAttribute("delMessage", notification.getFormattedErrors());
//            return "menu";
//        }
//        model.addAttribute("updUSucc", true);
        return "menu";
    }
//    @RequestMapping(value = "/user", params = "update", method = RequestMethod.POST)
//    public String changeMenu(Model model, @RequestParam long id, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
//
//        Notification<Boolean> notification = userService.updateUser(id, username, password, role);
//        if (!notification.getResult()) {
//            model.addAttribute("updUErr", true);
//            model.addAttribute("updMessage", notification.getFormattedErrors());
//            return "user";
//        }
//
//        model.addAttribute("updUSucc", true);
//        model.addAttribute("updMessage2", "User updated successfully!");
//        return "user";
//
//    }


}
