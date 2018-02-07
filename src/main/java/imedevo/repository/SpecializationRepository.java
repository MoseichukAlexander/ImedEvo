package imedevo.repository;

import imedevo.model.Specialization;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends CrudRepository<Specialization, Long> {

  Specialization findBySpecializationId(Long specializationId);

  Specialization findBySpecializationName(String specializationName);

  Specialization findBySpecializationNameEn(String specializationNameEn);

  Specialization save(Specialization specialization);

  @Query(value = "select * "
      + "from specializations "
      + "where match(specialization_ru, specialization_en) against(?1) and "
      + "match(specialization_ru, specialization_en) against(?1)=( "
      + "select max(match(specialization_ru, specialization_en) against(?1)) from specializations "
      + ")",
      nativeQuery = true)
  List<Specialization> findSimilarSpecialization(String specializationName);

}
