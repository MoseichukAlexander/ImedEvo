package imedevo.repository;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

import imedevo.model.TemporaryToken;
import javax.transaction.Transactional;

public interface TemporaryTokenRepository extends CrudRepository<TemporaryToken, Long> {

  TemporaryToken findByToken(String token);

  TemporaryToken findByUserEmail(String userEmail);

  void deleteByToken(String token);

  @Transactional
  void deleteByExpirationDateBefore(LocalDateTime expirationDate);
}
