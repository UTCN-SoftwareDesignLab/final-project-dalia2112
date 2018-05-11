package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Patient;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findById(long id);

    List<Patient> findAll();

}
