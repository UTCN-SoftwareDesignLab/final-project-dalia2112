package restaurant.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.model.Dish;
import restaurant.model.Ingredient;
import restaurant.model.User;
import restaurant.model.validation.Notification;
import restaurant.service.dish.DishService;
import restaurant.service.ingredient.IngredientService;
import restaurant.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    @Autowired
    private IngredientService ingredientService;


    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public String showAdminMenu() {
        return "adminPage";
    }

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
            model.addAttribute("updUErr", true);
            model.addAttribute("updMessage", notification.getFormattedErrors());
            return "user";
        }
        model.addAttribute("updUSucc", true);
        model.addAttribute("updMessage2", "User deleted successfully!");
        return "user";
    }


    @RequestMapping(value = "/logout", params = "logout", method = RequestMethod.GET)
    public String logout() {
        return "redirect:/login";
    }


    /****************  MENU  ****************/

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String showMenu() {
        ingredientService.createIngredients();
        dishService.createDishes();
        return "menu";
    }

    //ADD DISH
    @RequestMapping(value = "/menu", params = "ps", method = RequestMethod.POST)
    public String addDish(Model model,HttpServletRequest request, @RequestParam String name, @RequestParam float price) {

        Ingredient ingredient = ingredientService.findByName(request.getParameter("mySelect2")).get(0);
        Notification<Boolean> notification=dishService.addDish(name, ingredient, price);
        if (!notification.getResult()) {
            model.addAttribute("updUErr", true);
            model.addAttribute("updMessage", notification.getFormattedErrors());
            return "menu";
        }
        model.addAttribute("updUSucc", true);
        model.addAttribute("updMessage2","Dish added successfully!");
        return "menu";
    }


    //VIEW DISHES
    @RequestMapping(value = "/dishesView", params = "viewDishes", method = RequestMethod.GET)
    public String viewDishes(Model model, Authentication authentication) {
        List<Dish> items = dishService.findAll();
        model.addAttribute("dishes", items);
        return "/menu";
    }

    //UPDATE DISH
    @RequestMapping(value = "/menu", params = "update", method = RequestMethod.POST)
    public String updateDish(Model model, @RequestParam long id, @RequestParam String name, @RequestParam float price) {

        Notification<Boolean> notification = dishService.updateDish(id, name, price);
        if (!notification.getResult()) {
            model.addAttribute("updUErr", true);
            model.addAttribute("delMessage", notification.getFormattedErrors());
            return "menu";
        }
        model.addAttribute("updUSucc", true);
        model.addAttribute("updMessage2","Dish updates successfully!");
        return "menu";
    }

    //DELETE DISH
    @RequestMapping(value = "/menu", params = "dlt", method = RequestMethod.POST)
    public String deleteDish(Model model, @RequestParam long id) {

        Notification<Boolean> notification = dishService.deleteDish(id);

        if (!notification.getResult()) {
            model.addAttribute("updUErr", true);
            model.addAttribute("delMessage", notification.getFormattedErrors());
            return "menu";
        }
        model.addAttribute("updUSucc", true);
        model.addAttribute("updMessage2","Dish deleted successfully!");
        return "menu";
    }



}
