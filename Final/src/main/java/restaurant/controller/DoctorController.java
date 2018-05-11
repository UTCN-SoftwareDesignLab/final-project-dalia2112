package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.model.Consultation;
import restaurant.model.validation.Notification;
import restaurant.service.consultation.ConsultationService;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private ConsultationService consultationService;

    @RequestMapping(value = "/doctorOp", method = RequestMethod.GET)
    public String showSecretary() {
        return "doctorOp";
    }

    // ADD DETAILS TO CONSULTATION
    @RequestMapping(value = "/doctorOp", params = "addDet", method = RequestMethod.POST)
    public String addConsultDetails(Model model, @RequestParam long id, @RequestParam String details) {

        Notification<Boolean> notification = consultationService.addDetails(id, details);
        if (notification.getResult()) {
            model.addAttribute("detSucc", true);
            model.addAttribute("detMsg2", "Details added successfully!");
            return "doctorOp";
        }
        model.addAttribute("detErr", true);
        model.addAttribute("detMsg", notification.getFormattedErrors());
        return "doctorOp";
    }

    //VIEW CONSULTATION DETAILS
    @RequestMapping(value = "/consulView", params = "viewConsultations", method = RequestMethod.GET)
    public String showConsultations(Model model) {
        final List<Consultation> items = consultationService.findAll();
        model.addAttribute("consultations", items);
        return "doctorOp";
    }
}
