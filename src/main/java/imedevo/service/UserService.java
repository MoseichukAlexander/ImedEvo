package imedevo.service;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.httpStatuses.UserStatus;
import imedevo.model.Role;
import imedevo.model.User;
import imedevo.model.UserRole;
import imedevo.repository.UserRepository;
import imedevo.repository.UserRoleRepository;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

//import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private RolesService rolesService;

  public List<User> getAll() {
    List<User> listOfUsers = (List<User>) userRepository.findAll();
    for (User user : listOfUsers) {
      user.setUserRoles(rolesService.getUserRoles(user.getId()));
    }
    return listOfUsers;
  }

  public User getById(long id) throws UserNotFoundException {
    User user = userRepository.findOne(id);
    if (user == null) {
      throw new UserNotFoundException();
    }
    user.setUserRoles(rolesService.getUserRoles(id));
    return user;
  }

  @Transactional
  public Map<String, Object> save(User user) {
    Map<String, Object> map = new HashMap<>();

    if (user.getEmail() == null) {
      map.put("status", UserStatus.REGISTRATION_ERROR_EMPTY_EMAIL);
      return map;
    }

    if (userRepository.findByEmail(user.getEmail()) != null) {
      map.put("status", UserStatus.REGISTRATION_ERROR_DUPLICATE_USERS);
      return map;
    }

    if (user.getPassword() == null) {
      map.put("status", UserStatus.REGISTRATION_ERROR_INCORRECT_PASSWORD);
      return map;
    }
    map.put("status", UserStatus.ADD_USER_OK);
    map.put("user", userRepository.save(user));

    List<UserRole> userRoles = rolesService.getUserRoles(user.getId());
    userRoles.add(new UserRole(user.getId(), Role.USER));
    rolesService.save(userRoles);
    map.put("role", userRoles);
    return map;
  }

  @Transactional
  public Map<String, Object> updateUser(User updatedUser) {
    Map<String, Object> map = new HashMap<>();

    /** this is security checking */
//    if (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != updatedUser.getId()) {
//      map.put("status", UserStatus.ACCESS_DENIED);
//      return map;
//    }

    if (updatedUser.getEmail() != null) {
      User checkUserFromDb = userRepository.findByEmail(updatedUser.getEmail());
      if (checkUserFromDb != null && updatedUser.getId() != checkUserFromDb.getId()) {
        map.put("status", UserStatus.EDIT_PROFILE_ERROR);
        return map;
      }
    }

    User userFromDb = userRepository.findOne(updatedUser.getId());
    if (userFromDb == null) {
      map.put("status", UserStatus.NOT_FOUND);
    } else {
      Field[] fields = updatedUser.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        Object userFromDbValue = ReflectionUtils.getField(field, updatedUser);
        if (userFromDbValue != null) {
          ReflectionUtils.setField(field, userFromDb, userFromDbValue);
        }
      }
      map.put("status", UserStatus.EDIT_PROFILE_SUCCESS);
      map.put("user", userRepository.save(userFromDb));
    }
    return map;
  }

  @Transactional
  public void delete(long id) throws UserNotFoundException, AccessDeniedException {
    /** this is security checking */
//    if (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != id) {
//      throw new AccessDeniedException();
//    }

    if (userRepository.findOne(id) != null) {
      userRoleRepository.delete(userRoleRepository.findByUserId(id));
      userRepository.delete(id);
    } else {
      throw new UserNotFoundException();
    }
  }
}
