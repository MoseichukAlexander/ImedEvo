package imedevo.repository;

import org.springframework.data.repository.CrudRepository;

import imedevo.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByEmail(String email);

}
