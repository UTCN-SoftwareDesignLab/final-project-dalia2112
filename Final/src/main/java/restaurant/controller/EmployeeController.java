package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import restaurant.model.Orderr;
import restaurant.service.employee.EmployeeService;
import restaurant.service.orderr.OrderrService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderrService orderrService;

    @RequestMapping(value = "/maps", method = RequestMethod.GET)
    public String showEmployeeMenu() {
        return "maps";
    }

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public String showOrders(Model model, HttpServletRequest request) {
        System.out.println(orderrService.findAll().size());
        List<Orderr> orderrList=employeeService.getAllProcessedOrders();
        orderrList.removeAll(employeeService.getDeliveredOrders());
        model.addAttribute("orders", orderrList);
        model.addAttribute("orders2", employeeService.getDeliveredOrders());
        model.addAttribute("list", employeeService.getAvailableCars());
//        System.out.println(request.getParameterValues("cbx").length);
//        System.out.println(request.getParameter("msl"));
        return "delivery";
    }

    //set cars with orders => MAX 2 ORDERS/CAR
    @RequestMapping(value = "/delivery", params = "rew", method = RequestMethod.POST)
    public String setCars(Model model, HttpServletRequest request, Authentication authentication) {
        String[] orderIds = request.getParameterValues("cbx");
        if (orderIds.length == 0) {
            return "delivery";
        }
        for (int i = 0; i < orderIds.length; i++) {
            Orderr orderr = orderrService.findById(Long.parseLong(orderIds[i]));
            System.out.println(orderIds[i] + " " + request.getParameter(orderIds[i]));
            employeeService.setCarToOrder(Integer.parseInt(request.getParameter(orderIds[i]).substring(4, 5)), Long.parseLong(orderIds[i]));
        }
        return "delivery";
    }
}
