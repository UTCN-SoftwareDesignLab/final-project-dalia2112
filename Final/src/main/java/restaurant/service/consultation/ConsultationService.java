package restaurant.service.consultation;

import org.springframework.stereotype.Service;
import restaurant.model.Consultation;
import restaurant.model.Patient;
import restaurant.model.validation.Notification;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ConsultationService {
    List<Consultation> findAll();

    Consultation findById(long id);

//    List<Consultation> findByDoctorId(long docId);

//    Notification<Boolean> addConsultation(String date, long patId, long docId);

//    Notification<Boolean> updateConsultation(long id, String date, long patId, long docId);

//    List<Consultation> findByDate(LocalDate date);

    boolean doctorWorksOnDate(long docId, String date);

    Notification<Boolean> deleteConsultation(long id);

    Notification<Boolean> addDetails(long id, String details);

    Notification<String> checkInPatient(long id);

//    List<Patient> checkedInPatients();
}
