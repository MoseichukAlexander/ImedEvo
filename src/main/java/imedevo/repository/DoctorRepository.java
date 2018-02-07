package imedevo.repository;


import imedevo.model.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

  Doctor findById(long id);

  Doctor findByUserId(long userId);

  List<Doctor> findAll();

  @Query(value = "SELECT * "
      + "FROM doctors d "
      + "JOIN users u ON d.user_id = u.id"
      + "JOIN specializations s ON s.id = d.specialization_id "
      + "WHERE (specialization_ru) LIKE (?1)",
      nativeQuery = true)
  List<Doctor> findByDoctorSpecialization(String doctorParams);

//  @Query(value = "SELECT * "
//      + "FROM doctors "
//      + "JOIN users ON users.id = doctors.user_id WHERE (last_name) LIKE (?1)"
//  )
//  List<Doctor> findByDoctorName(String doctorParams);

}
