package restaurant.model.validation;

import restaurant.model.Consultation;
import restaurant.model.Patient;
import restaurant.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationValidator {

    private final Consultation consultation;
    private List<String> errors;

    public ConsultationValidator(Consultation consultation) {
        this.consultation = consultation;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateDate(consultation.getDate());
        validateDoctor(consultation.getDoctor());
        validatePatient(consultation.getPatient());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateDate(LocalDate date) {
        if (LocalDate.now().getYear() > date.getYear())
            errors.add("Invalid date! Appointments must be done in the future!");
    }

    private void validateDoctor(User doctor) {
        if (doctor == null) {
            errors.add("The doctor does not exist!");
            return;
        }
        if (!doctor.getRole().equalsIgnoreCase("doctor")) {
            errors.add("The doctor is not really a doctor..");
        }
    }

    private void validatePatient(Patient patient) {
        if (patient == null) {
            errors.add("The patient does not exist!");
        }
    }


    public String getFormattedErrors() {
        String result = "";
        for (String error : getErrors())
            result += error + "\n \n";
        return result;
    }
}
