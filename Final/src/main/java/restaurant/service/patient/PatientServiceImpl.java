package restaurant.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.model.Patient;
import restaurant.model.validation.Notification;
import restaurant.model.validation.PatientValidator;
import restaurant.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(long id) {
        return patientRepository.findById(id);
    }

//    @Override
//    public Notification<Boolean> addPatient(String name, long icn, long pnc, String bday, String address) {
//        LocalDate bd = LocalDate.parse(bday);
//        Patient patient = new PatientBuilder()
//                .setName(name)
//                .setId_card_nr(icn)
//                .setPers_num_code(pnc)
//                .setBirthday(bd)
//                .setAddress(address)
//                .build();
//        Notification<Boolean> notification = new Notification<>();
//        PatientValidator validator = new PatientValidator(patient);
//        if (validator.validate()) {
//            notification.setResult(true);
//            patientRepository.save(patient);
//        } else {
//            validator.getErrors().forEach(notification::addError);
//            notification.setResult(false);
//        }
//        return notification;
//    }

//    @Override
//    public Notification<Boolean> updatePatient(long id, String name, long icn, long pnc, String bday, String address) {
//        LocalDate date = LocalDate.parse(bday);
//        Notification<Boolean> notification = new Notification<>();
//        if (patientRepository.findById(id) != null) {
//            Patient patient = new PatientBuilder()
//                    .setId(id)
//                    .setName(name)
//                    .setId_card_nr(icn)
//                    .setPers_num_code(pnc)
//                    .setBirthday(date)
//                    .setAddress(address)
//                    .build();
//            PatientValidator validator = new PatientValidator(patient);
//            if (validator.validate()) {
//                notification.setResult(true);
//                patientRepository.save(patient);
//            } else {
//                validator.getErrors().forEach(notification::addError);
//                notification.setResult(false);
//            }
//            return notification;
//        }
//        notification.addError("Patient does not exist!");
//        return notification;
//    }
}
