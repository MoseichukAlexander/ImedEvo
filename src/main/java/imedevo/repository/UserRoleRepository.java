package imedevo.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import imedevo.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

  List<UserRole> findByUserId(Long userId);
}
