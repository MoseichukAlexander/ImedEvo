package imedevo.service;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.httpStatuses.UserStatus;
import imedevo.model.Image;
import imedevo.model.Role;
import imedevo.model.User;
import imedevo.model.UserRole;
import imedevo.repository.ImageRepository;
import imedevo.repository.UserRepository;
import imedevo.repository.UserRoleRepository;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private RolesService rolesService;

  @Autowired
  private ImageRepository imageRepository;

  private final String status = "status";


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
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_EMAIL);
      return map;
    }

    if (userRepository.findByEmail(user.getEmail()) != null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_DUPLICATE_USERS);
      return map;
    }

    if (user.getPassword() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_INCORRECT_PASSWORD);
      return map;
    }
    user.setDateOfRegistration(LocalDateTime.now());
    map.put(status, UserStatus.ADD_USER_OK);
    map.put("user", userRepository.save(user));

    List<UserRole> userRoles = rolesService.getUserRoles(user.getId());
    userRoles.add(new UserRole(user.getId(), Role.USER));
    rolesService.save(userRoles);
    map.put("role", userRoles);
    return map;
  }

  @Transactional
  public Map<String, Object> updateUser(User updatedUser) throws UserNotFoundException {
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
        map.put(status, UserStatus.EDIT_PROFILE_ERROR);
        return map;
      }
    }

    User userFromDb = userRepository.findOne(updatedUser.getId());
    if (userFromDb == null) {
      map.put(status, UserStatus.NOT_FOUND);
    } else {
      Field[] fields = updatedUser.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        Object userFromDbValue = ReflectionUtils.getField(field, updatedUser);
        if (userFromDbValue != null) {
          ReflectionUtils.setField(field, userFromDb, userFromDbValue);
        }
      }
      map.put(status, UserStatus.EDIT_PROFILE_SUCCESS);
      map.put("user", userRepository.save(userFromDb));
    }
    return map;
  }

  @Transactional
  public Map<String, Object> deleteUser(long id)
      throws UserNotFoundException, AccessDeniedException {
    /** this is security checking */
//    if (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != id) {
//      throw new AccessDeniedException();
//    }
    Map<String, Object> map = new HashMap<>();
    if (userRepository.findOne(id) != null) {
      userRoleRepository.delete(userRoleRepository.findByUserId(id));
      userRepository.delete(id);
      map.put(status, UserStatus.DELETE_PROFILE_SUCCESS);
      return map;
    } else {
      throw new UserNotFoundException();
    }
  }

  public Map<String, Object> uploadImage(long userId, MultipartFile imageFile) {

    System.out.println("userId = " + userId);
    System.out.println("file = " + imageFile.getOriginalFilename());

    String fileName = "";
    String link = "";

    Logger logger = LogManager.getLogger(getClass());
    String uploadImageFolder = "testfolder";
    Map<String, Object> map = new HashMap<>();

    if (!imageFile.isEmpty()) {
      try {
        if (!Files.exists(Paths.get(uploadImageFolder))) {
          Files.createDirectory(Paths.get(uploadImageFolder));
        }

        fileName = imageFile.getOriginalFilename();
        Files.copy(imageFile.getInputStream(),
            Paths.get(uploadImageFolder,
                String.format("(%s)%s", Instant.now().getEpochSecond(), fileName)));
        link = uploadImageFolder + "/" + fileName;
        imageRepository.save(new Image(userId, link));
        map.put("status", UserStatus.IMAGE_UPLOAD_SUCCESS);
      } catch (IOException ex) {
        logger.error(ex.getMessage(), ex);
      }
    } else {
      map.put("status", UserStatus.IMAGE_IS_EMPTY);
    }
    return map;
  }

  public Map<String, Object> login(String email, String password) {
    Map<String, Object> map = new HashMap<>();
    User user = userRepository.findByEmail(email);
    if (user == null || !user.getPassword().equals(password)) {
      map.put("status", UserStatus.LOGIN_BAD_LOGIN);
    } else {
      map.put("status", UserStatus.LOGIN_OK);
      map.put("user", user);
    }
    return map;
  }
}