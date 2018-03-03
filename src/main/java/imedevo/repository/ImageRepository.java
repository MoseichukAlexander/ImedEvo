package imedevo.repository;

import imedevo.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {

  Image findByUserId(long userId);
}
