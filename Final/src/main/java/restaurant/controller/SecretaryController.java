package restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.Greeting;
import restaurant.model.Consultation;
import restaurant.model.Patient;
import restaurant.model.validation.Notification;
import restaurant.service.consultation.ConsultationService;
import restaurant.service.patient.PatientService;

import java.util.List;


@Controller
public class SecretaryController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private ConsultationService consultationService;

    /*********************ADD / UPDATE PATIENT*******************/

    @RequestMapping(value = "/secretaryOp", method = RequestMethod.GET)
    public String showSecretary() {
        return "secretaryOp";
    }


    // ADD PATIENT
//    @RequestMapping(value = "/secretaryOp", params = "addP", method = RequestMethod.POST)
//    public String addPatient(Model model, @RequestParam String name, @RequestParam long icn, @RequestParam long pnc, @RequestParam String bday, @RequestParam String addr) {
//
//
//        System.out.println(name + " " + icn + " " + pnc + " " + bday + " " + addr);
//        Notification<Boolean> notification = patientService.addPatient(name, icn, pnc, bday, addr);
////        System.out.println(notification.getFormattedErrors());
////        JOptionPane.showMessageDialog(null,notification.getFormattedErrors());
//        if (notification.getResult()) {
//            model.addAttribute("updUSucc", true);
//            model.addAttribute("updMessage2", "Patient added successfully!");
//        } else {
//            model.addAttribute("updUErr", true);
//            model.addAttribute("updMessage", notification.getFormattedErrors());
//        }
//        return "secretaryOp";
//    }

//    //UPDATE PATIENT
//    @RequestMapping(value = "/secretaryOp", params = "update", method = RequestMethod.POST)
//    public String updatePatient(Model model, @RequestParam long id, @RequestParam String name, @RequestParam long icn, @RequestParam long pnc, @RequestParam String bday, @RequestParam String addr) {
//
//        Notification<Boolean> notification = patientService.updatePatient(id, name, icn, pnc, bday, addr);
//        if (notification.getResult()) {
//            model.addAttribute("updUSucc", true);
//            model.addAttribute("updMessage2", "Patient updated successfully!");
//        } else {
//            model.addAttribute("updUErr", true);
//            model.addAttribute("updMessage", notification.getFormattedErrors());
//        }
//        return "secretaryOp";
//    }

    //VIEW PATIENTS
    @RequestMapping(value = "/patientView", params = "viewPatients", method = RequestMethod.GET)
    public String findAllPatients(Model model) {
        final List<Patient> items = patientService.findAll();
        model.addAttribute("patients", items);
        return "secretaryOp";
    }


    /********************CONSULTATIONS********************/

//    // ADD CONSULTATION
//    @RequestMapping(value = "/makeAppointment", method = RequestMethod.GET)
//    public String addConsultation(Model model, @RequestParam String cday, @RequestParam long idPat, @RequestParam long idDoc) {
//
//        if (consultationService.doctorWorksOnDate(idDoc, cday)) {
//            model.addAttribute("conErr", true);
//            model.addAttribute("coneMsg", "Busy doctor on that day!");
//            return "secretaryOp";
//        }
//        Notification<Boolean> notification = consultationService.addConsultation(cday, idPat, idDoc);
//        if (notification.getResult()) {
//            model.addAttribute("conSucc", true);
//            model.addAttribute("consMsg", "Consultation added successfully!");
//            return "secretaryOp";
//        }
//        model.addAttribute("conErr", true);
//        model.addAttribute("coneMsg", notification.getFormattedErrors());
//
//        return "secretaryOp";
//    }

//    //UPDATE CONSULTATION
//    @RequestMapping(value = "/secretaryOp", params = "updCon", method = RequestMethod.POST)
//    public String updateConsultation(Model model, @RequestParam long idCon, @RequestParam String cday, @RequestParam long idPat, @RequestParam long idDoc) {
//
//        Notification<Boolean> notification = consultationService.updateConsultation(idCon, cday, idPat, idDoc);
//        if (notification.getResult()) {
//            model.addAttribute("conSucc", true);
//            model.addAttribute("consMsg", "Consultation updated successfully!");
//        } else {
//            model.addAttribute("conErr", true);
//            model.addAttribute("coneMsg", notification.getFormattedErrors());
//        }
//        return "secretaryOp";
//    }

    //DELETE CONSULTATION
    @RequestMapping(value = "/secretaryOp", params = "delCon", method = RequestMethod.POST)
    public String deleteConsultation(Model model, @RequestParam long idCon) {

        Notification<Boolean> notification = consultationService.deleteConsultation(idCon);
        if (notification.getResult()) {
            model.addAttribute("conSucc", true);
            model.addAttribute("consMsg", "Consultation deleted successfully!");
        } else {
            model.addAttribute("conErr", true);
            model.addAttribute("coneMsg", notification.getFormattedErrors());
        }
        return "secretaryOp";
    }

    //VIEW CONSULTATIONS
    @RequestMapping(value = "/consultView", params = "viewConsultations", method = RequestMethod.GET)
    public String showConsultations(Model model) {
        final List<Consultation> items = consultationService.findAll();
        model.addAttribute("consultations", items);
        return "secretaryOp";
    }

    @RequestMapping(value = "/informDoc", method = RequestMethod.GET)
    public String showindex() {
        return "informDoc";
    }


    @MessageMapping("/hi")
    @SendTo("/topic/greetings")
    @RequestMapping(value = "/secretaryOp", method = RequestMethod.POST)
    public Greeting checkInPatient(String message) {
        message = message.replace("{", "");
        message = message.replace("}", "");
        message = message.replace("\"", "");
        return new Greeting(message);
    }

//    @MessageMapping("/hi")
//    @SendTo("/topic/greetings")
//    @RequestMapping(value = "/secretaryOp", params = "send", method = RequestMethod.POST)
//    public Greeting todayPatients() {
//
//        List<Consultation> consultationList=consultationService.findByDate(LocalDate.now());
//
//        String message = consultationList.get(0).getPatient().getName()+" "+consultationList.get(0).getDate();
//        return new Greeting(message);
//    }


}
