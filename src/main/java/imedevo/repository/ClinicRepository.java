package imedevo.repository;

import imedevo.model.Clinic;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

  Optional<Clinic> findById(Long id);

  Clinic findByEmail(String email);

  @Query(value = "SELECT *"
      + "FROM clinics "
      + "WHERE (clinic_name) LIKE (?1)",
      nativeQuery = true)
  List<Clinic> findByClinicName(String clinicName);


}
