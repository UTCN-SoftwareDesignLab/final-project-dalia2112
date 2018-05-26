package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.model.Ingredient;
import restaurant.model.validation.Notification;
import restaurant.service.dish.DishService;
import restaurant.service.ingredient.IngredientService;
import restaurant.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

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
    public String showUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    //UPDATE USER
    @RequestMapping(value = "/user", params = "update", method = RequestMethod.GET)
    public String updateUser(Model model, @RequestParam long id, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        Notification<Boolean> notification = userService.updateUser(id, username, password, role);
        if (!notification.getResult()) {
            model.addAttribute("updMessage", notification.getFormattedErrors());
        }
        model.addAttribute("updMessage2", "User updated successfully!");
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    //DELETE USER
    @RequestMapping(value = "/user", params = "dlt", method = RequestMethod.POST)
    public String deleteUser(Model model, @RequestParam long id) {

        Notification<Boolean> notification = userService.deleteUser(id);
        if (!notification.getResult()) {
            model.addAttribute("updMessage", notification.getFormattedErrors());
        }
        model.addAttribute("updMessage2", "User deleted successfully!");
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    /****************  MENU  ****************/

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String showMenu(Model model) {
        ingredientService.createIngredients();
        dishService.createDishes();
        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }

    //ADD DISH
    @RequestMapping(value = "/menu", params = "ps", method = RequestMethod.POST)
    public String addDish(Model model, HttpServletRequest request, @RequestParam String name, @RequestParam float price) {
        Ingredient ingredient = ingredientService.findByName(request.getParameter("mySelect2")).get(0);
        Notification<Boolean> notification = dishService.addDish(name, ingredient, price);
        if (!notification.getResult()) {
            model.addAttribute("updMessage", notification.getFormattedErrors());
        }
        model.addAttribute("updMessage2", "Dish added successfully!");
        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }

    //UPDATE DISH
    @RequestMapping(value = "/menu", params = "update", method = RequestMethod.POST)
    public String updateDish(Model model, @RequestParam long id, @RequestParam String name, @RequestParam float price) {

        Notification<Boolean> notification = dishService.updateDish(id, name, price);
        if (!notification.getResult()) {
            model.addAttribute("delMessage", notification.getFormattedErrors());
        }
        model.addAttribute("updMessage2", "Dish updates successfully!");
        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }

    //DELETE DISH
    @RequestMapping(value = "/menu", params = "dlt", method = RequestMethod.POST)
    public String deleteDish(Model model, @RequestParam long id) {

        Notification<Boolean> notification = dishService.deleteDish(id);

        if (!notification.getResult()) {
            model.addAttribute("delMessage", notification.getFormattedErrors());
        }
        model.addAttribute("updMessage2", "Dish deleted successfully!");
        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }
}
