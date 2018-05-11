package restaurant.service.consultation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Consultation;
import restaurant.model.Patient;
import restaurant.model.User;
import restaurant.model.validation.ConsultationValidator;
import restaurant.model.validation.Notification;
import restaurant.repository.ConsultationRepository;
import restaurant.repository.PatientRepository;
import restaurant.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, UserRepository userRepository, PatientRepository patientRepository) {
        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Consultation findById(long id) {
        return consultationRepository.findById(id);
    }

//    @Override
//    public List<Consultation> findByDoctorId(long docId) {
//        return consultationRepository.findByDoctorId(docId);
//    }

//    @Override
//    public Notification<Boolean> addConsultation(String date, long patId, long docId) {
//        LocalDate date1 = LocalDate.parse(date);
//        Notification<Boolean> notification = new Notification<>();
//        User doctor = userRepository.findById(docId);
//        Patient patient = patientRepository.findById(patId);
//        Consultation consultation = new ConsultationBuilder()
//                .setDate(date1)
//                .setDoctor(doctor)
//                .setPatient(patient)
//                .setDetails("")
//                .build();
//        ConsultationValidator validator = new ConsultationValidator(consultation);
//        if (validator.validate()) {
//            notification.setResult(true);
//            consultationRepository.save(consultation);
//        } else {
//            notification.setResult(false);
//            validator.getErrors().forEach(notification::addError);
//        }
//        return notification;
//    }

//    @Override
//    public Notification<Boolean> updateConsultation(long id, String date, long patId, long docId) {
//        LocalDate date1 = LocalDate.parse(date);
//        Notification<Boolean> notification = new Notification<>();
//        Consultation consultation = consultationRepository.findById(id);
//        if (consultation == null) {
//            notification.addError("Consultation does not exist!");
//            notification.setResult(false);
//            return notification;
//        }
//
//        User doctor = userRepository.findById(docId);
//        Patient patient = patientRepository.findById(patId);
//        Consultation consultation1 = new ConsultationBuilder()
//                .setDate(date1)
//                .setDoctor(doctor)
//                .setPatient(patient)
//                .setDetails("")
//                .build();
//        ConsultationValidator validator = new ConsultationValidator(consultation1);
//        if (validator.validate()) {
//            consultationRepository.save(consultation1);
//            notification.setResult(true);
//        } else {
//            notification.setResult(false);
//            validator.getErrors().forEach(notification::addError);
//        }
//        return notification;
//    }

//    @Override
//    public List<Consultation> findByDate(LocalDate date) {
//        return consultationRepository.findByDate(date);
//    }

    public boolean doctorWorksOnDate(long docId, String date) {
        LocalDate date1 = LocalDate.parse(date);
        List<Consultation> consultationList = consultationRepository.findByDate(date1);
        for (Consultation c : consultationList) {
            if (c.getDoctor().getId() == docId)
                return true;
        }
        return false;
    }

    public Notification<Boolean> deleteConsultation(long id) {
        Notification<Boolean> notification = new Notification<>();
        if (id < 0) {
            notification.addError("Id must be positive!");
            notification.setResult(false);
            return notification;
        }
        consultationRepository.deleteById(id);
        notification.setResult(true);
        return notification;
    }

    public Notification<Boolean> addDetails(long id, String details) {
        Notification<Boolean> notification = new Notification<>();
        if (id < 0) {
            notification.addError("Id must be positive!");
            notification.setResult(false);
            return notification;
        }
        Consultation consultation = findById(id);
        if (consultation == null) {
            notification.addError("Consultation does not exist!");
            notification.setResult(false);
            return notification;
        }
        if (consultation.getDate().isBefore(LocalDate.now())) {
            consultation.setDetails(details);
            consultationRepository.save(consultation);
            notification.setResult(true);
        } else {
            notification.addError("This is not a past consultation!");
            notification.setResult(false);
        }

        return notification;
    }

    public Notification<String> checkInPatient(long id) {
        Notification<String> notification = new Notification<>();
        if (id < 0) {
            notification.addError("Id must be positive!");
            notification.setResult("");
            return notification;
        }
        if (patientRepository.findById(id) == null) {
            notification.addError("Id must be positive!");
            notification.setResult("");
            return notification;
        }
        Patient patient = patientRepository.findById(id);
        List<Consultation> consultations = consultationRepository.findByDate(LocalDate.now());
        if (consultations == null) {
            notification.addError("No consultations today!");
            notification.setResult("");
            return notification;
        }
        for (Consultation consultation : consultations) {
            if (consultation.getPatient().getId() == id) {
                notification.setResult("Patient (" + id + ") " + patient.getName() + " has an consultation today!");
                return notification;
            }
        }
        notification.addError("This patient does not have an appointment today!");
        notification.setResult("");
        return notification;
    }

    public List<Patient> checkedInPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<Patient> checkedPatients = new ArrayList<>();
        for (Patient patient : patients) {
            if (!checkInPatient(patient.getId()).hasErrors())
                checkedPatients.add(patient);
        }
        return checkedPatients;
    }
}
