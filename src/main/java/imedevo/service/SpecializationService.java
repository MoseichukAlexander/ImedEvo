package imedevo.service;


import imedevo.model.Specialization;
import imedevo.repository.SpecializationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for (@link Specialization) class.
 */

@Service
public class SpecializationService {

  @Autowired
  SpecializationRepository specializationRepository;

  public Specialization findById(Long id) {
    return specializationRepository.findOne(id);
  }

  public Optional<Specialization> getSpecialization(String specialization) {

    List<Specialization> selectiedSpecialization = specializationRepository
        .findSimilarSpecialization(specialization);
    if (selectiedSpecialization.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(selectiedSpecialization.get(0));
    }
  }


}
