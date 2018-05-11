package restaurant.model.validation;

import restaurant.model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientValidator {

    private static final int ID_CARD_NR_LENGTH = 5;
    private static final int PERS_NUM_CODE_LENGTH = 5;
    private static final String PERS_NUM_CODE_START1 = "1";
    private static final String PERS_NUM_CODE_START2 = "2";
    private static final int MIN_AGE = 18;
    private final Patient patient;
    private List<String> errors;


    public PatientValidator(Patient patient) {
        this.patient = patient;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validate_IdCardNr(patient.getId_card_nr());
        validate_PersNumCode(patient.getPers_num_code());
        validate_Birthday(patient.getBirthday());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validate_IdCardNr(long icn) {
        if (String.valueOf(icn).length() != ID_CARD_NR_LENGTH)
            errors.add("Id card nr length must be 8!");
    }

    private void validate_PersNumCode(long pnc) {
        String pncString = String.valueOf(pnc);
        if (pncString.length() != PERS_NUM_CODE_LENGTH)
            errors.add("Pers num code length must be 8!");
        if (!pncString.substring(0, 1).equalsIgnoreCase(PERS_NUM_CODE_START1) && !pncString.substring(0, 1).equalsIgnoreCase(PERS_NUM_CODE_START2))
            errors.add("Pers num code must start with either 1 or 2!");
    }

    private void validate_Birthday(LocalDate bDay) {
        if (LocalDate.now().getYear() - bDay.getYear() < MIN_AGE)
            errors.add("Patient too young!");
    }

    public String getFormattedErrors() {
        String result = "";
        for (String error : getErrors())
            result += error + "\n \n";
        return result;
    }
}
