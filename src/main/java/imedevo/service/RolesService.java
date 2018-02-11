package imedevo.service;

import imedevo.model.UserRole;
import imedevo.repository.UserRoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

  @Autowired
  private UserRoleRepository userRoleRepository;

  public List<UserRole> getUserRoles(long id) {
    return userRoleRepository.findByUserId(id);
  }

  public UserRole save(UserRole userRole) {
    return userRoleRepository.save(userRole);
  }

  public Iterable<UserRole> save(List<UserRole> userRoles) {
    return userRoleRepository.save(userRoles);
  }
}
