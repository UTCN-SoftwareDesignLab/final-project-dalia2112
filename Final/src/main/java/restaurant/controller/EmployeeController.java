package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import restaurant.service.employee.EmployeeService;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employeePage", method = RequestMethod.GET)
    public String showEmployeeMenu() {
        return "employeePage";
    }

//    @RequestMapping(value = "/orders", method = RequestMethod.GET)
//    public String showOrderseMenu() {
//        return "orders";
//    }
}
