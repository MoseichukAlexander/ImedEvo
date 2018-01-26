package imedevo.repository;

import imedevo.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findById(Long id);
    Clinic findByEmail(String email);


}
