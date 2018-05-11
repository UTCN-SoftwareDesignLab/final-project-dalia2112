package restaurant.service.patient;

import org.springframework.stereotype.Service;
import restaurant.model.Patient;
import restaurant.model.validation.Notification;

import java.util.List;

@Service
public interface PatientService {

    List<Patient> findAll();

    Patient findById(long id);

//    Notification<Boolean> addPatient(String name, long icn, long pnc, String bday, String address);
//
//    Notification<Boolean> updatePatient(long id, String name, long icn, long pnc, String bday, String address);

}
