package imedevo.repository;


import imedevo.model.Doctor;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

  Doctor findById(long id);

  Doctor findByUserId(long userId);

  List<Doctor> findAll();

}
