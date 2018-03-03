package imedevo.service;

import imedevo.configuration.WebSecurityConfig;
import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.httpStatuses.UserStatus;
import imedevo.model.AppUser;
import imedevo.model.ChangePassword;
import imedevo.model.Image;
import imedevo.model.Role;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  WebSecurityConfig webSecurityConfig;

  @Autowired
  private GeocodingService geocoding;

  private final String status = "status";

  public List<AppUser> getAll() {
    List<AppUser> listOfUsers = (List<AppUser>) userRepository.findAll();
    for (AppUser user : listOfUsers) {
      user.setUserRoles(rolesService.getUserRoles(user.getId()));
      user.setPassword("not displayed");
    }
    return listOfUsers;
  }

  public AppUser getById(long id) throws UserNotFoundException {
    AppUser user = userRepository.findOne(id);
    if (user == null) {
      throw new UserNotFoundException();
    }
    user.setUserRoles(rolesService.getUserRoles(id));
    user.setImage(imageRepository.findByUserId(id));
    user.setPassword("not displayed");
    return user;
  }

  @Transactional
  public Map<String, Object> save(AppUser appUser) {
    Map<String, Object> map = new HashMap<>();

    if (appUser.getUsername() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_EMAIL);
      return map;
    }

    if (userRepository.findByUsername(appUser.getUsername()) != null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_DUPLICATE_USERS);
      return map;
    }

    if (appUser.getPassword() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_INCORRECT_PASSWORD);
      return map;
    }

    if (appUser.getCity() != null) {
      appUser.setCity(geocoding.getGeopositionByAddress(appUser.getCity()).getAddress());
    }

    appUser.setDateOfRegistration(LocalDate.now().toString());

    map.put(status, UserStatus.ADD_USER_OK);
    map.put("appUser", userRepository.save(appUser));

    List<UserRole> userRoles = rolesService.getUserRoles(appUser.getId());
    userRoles.add(new UserRole(appUser.getId(), Role.USER));
    rolesService.save(userRoles);
    map.put("role", userRoles);
    return map;
  }

  @Transactional
  public Map<String, Object> updateUser(AppUser updatedUser) throws UserNotFoundException {
    Map<String, Object> map = new HashMap<>();

    /** this is security checking */
//    if (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != updatedUser.getId()) {
//      map.put("status", UserStatus.ACCESS_DENIED);
//      return map;
//    }

    if (updatedUser.getUsername() != null) {
      AppUser checkUserFromDb = userRepository.findByUsername(updatedUser.getUsername());
      if (checkUserFromDb != null && updatedUser.getId() != checkUserFromDb.getId()) {
        map.put(status, UserStatus.EDIT_PROFILE_ERROR);
        return map;
      }
    }

    AppUser userFromDb = userRepository.findOne(updatedUser.getId());
    if (userFromDb == null) {
      map.put(status, UserStatus.NOT_FOUND);
    } else {
      if (updatedUser.getCity() != null) {
        updatedUser.setCity(geocoding.getGeopositionByAddress(updatedUser.getCity()).getAddress());
      }
      Field[] fields = updatedUser.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        if (field.getName().equals("id") || field.getName().equals("password") ||
            field.getName().equals("dateOfRegistration") || field.getName().equals("userRoles")) {
          continue;
        }
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

    String fileName = "";
    String link = "";
    String imageLink = "https://imed.od.ua/assets/images/";
    String uploadImageFolder = "src/main/resources/static/assets/images";

    Logger logger = LogManager.getLogger(getClass());
    Map<String, Object> map = new HashMap<>();

    if (imageFile.isEmpty()) {
      map.put("status", UserStatus.IMAGE_IS_EMPTY);
      return map;
    }

    try {
      if (!Files.exists(Paths.get(uploadImageFolder))) {
        Files.createDirectory(Paths.get(uploadImageFolder));
      }

      fileName = imageFile.getOriginalFilename();
      Files.copy(imageFile.getInputStream(),
          Paths.get(uploadImageFolder,
              String.format("(%s)%s", Instant.now().getEpochSecond(), fileName)));
      link = imageLink + fileName;

      Image image = imageRepository.findByUserId(userId);
      if (image != null) {
        image.setLink(link);
        imageRepository.save(image);
        map.put("status", UserStatus.IMAGE_UPLOAD_SUCCESS);
      } else {
        map.put("status", UserStatus.NOT_FOUND);
      }
    } catch (IOException ex) {
      logger.error(ex.getMessage(), ex);
    }
    return map;
  }

  public Map<String, Object> login(String username, String password) {
    Map<String, Object> map = new HashMap<>();
    AppUser userFromRepository = userRepository.findByUsername(username);
    if (userFromRepository == null || !bCryptPasswordEncoder
        .matches(password, userFromRepository.getPassword())) {
      map.put("status", UserStatus.LOGIN_BAD_LOGIN);
    } else {
      map.put("status", UserStatus.LOGIN_OK);
      map.put("user", userFromRepository);

    }
    return map;
  }

  public Map<String, Object> changePassword(ChangePassword changePassword)
      throws AccessDeniedException {
    Map<String, Object> map = new HashMap<>();
    if (changePassword.getEmail() == null || changePassword.getPassword() == null) {
      map.put("status", UserStatus.PASSWORD_CHANGE_REJECTED);
      return map;
    }
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    System.out.println(email);
    AppUser user = userRepository.findByUsername(changePassword.getEmail());
    if (user.getUsername().equals(email)) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      map.put("status", UserStatus.PASSWORD_CHANGE_OK);
      map.put("user", userRepository.save(user));
    } else {
      throw new AccessDeniedException();
    }
    return map;
  }
}