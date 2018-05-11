package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.service.client.ClientService;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String showSecretary() {
        return "client";
    }

    // ADD CLIENT
    @RequestMapping(value = "/client", params = "addClient", method = RequestMethod.POST)
    public String addClient(Model model, @RequestParam String name, @RequestParam float money,@RequestParam long orderId) {

        //Notification<Boolean> notification = consultationService.addDetails(id, details);
//        if (notification.getResult()) {
//            model.addAttribute("detSucc", true);
//            model.addAttribute("detMsg2", "Details added successfully!");
//            return "client";
//        }
//        model.addAttribute("detErr", true);
//        model.addAttribute("detMsg", notification.getFormattedErrors());
        clientService.addClient(name,money,orderId);
        return "client";
    }
}
