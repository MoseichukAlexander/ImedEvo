package imedevo.service;

import imedevo.httpStatuses.UserStatus;
import imedevo.model.AppUser;
import imedevo.model.Role;
import imedevo.model.UserRole;
import imedevo.repository.UserRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

  private Logger logger = LogManager.getLogger(getClass());

  private final int lengthOfUserPassword = 6;
  private final String status = "status";

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailSenderService mailSenderService;

  @Autowired
  private RolesService rolesService;

  @Autowired
  public BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private GeocodingService geocoding;

  @Transactional
  public Map<String, Object> createNewUserInDB(AppUser appUser) {
    Map<String, Object> map = new HashMap<>();

    if (getUserByEmail(appUser) != null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_DUPLICATE_USERS);
      return map;
    }

    if (appUser.getUsername() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_EMAIL);
      return map;
    }

    if (!isEmailValid(appUser.getUsername())) {
      map.put(status, UserStatus.REGISTRATION_ERROR_INCORRECT_EMAIL);
      return map;
    }

    if (appUser.getPhone() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_PHONE);
      return map;
    }

    if (appUser.getPhone().length() != 13 || appUser.getPhone().charAt(0) != '+') {
      map.put(status, UserStatus.REGISTRATION_ERROR_PHONE_INVALID);
      return map;
    }

    if (appUser.getFirstName() == null || appUser.getLastName() == null
        || appUser.getFirstName().length() < 2 || appUser.getLastName().length() < 2) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_FIRSTNAME_OR_LASTNAME);
      return map;
    }

    if (appUser.getBirthDate() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_BIRTHADAY);
      return map;
    }

    if (appUser.getBirthDate().toLocalDate().isBefore(LocalDate.now().minusYears(100)) ||
        appUser.getBirthDate().toLocalDate().isAfter(LocalDate.now())) {
      map.put(status, UserStatus.REGISTRATION_ERROR_BIRTHDAY_INVALID);
      return map;
    }

    if (!isCorrectPassword(appUser)) {
      map.put(status, UserStatus.REGISTRATION_ERROR_INCORRECT_PASSWORD);
      return map;
    }

    //TODO
    if (appUser.getCity() != null) {
      appUser.setCity(geocoding.getGeopositionByAddress(appUser.getCity()).getAddress());
    }

    appUser.setDateOfRegistration(LocalDate.now().toString());
    userRepository.save(appUser);
    List<UserRole> userRoles = rolesService.getUserRoles(appUser.getId());
    userRoles.add(new UserRole(appUser.getId(), Role.USER));
    rolesService.save(userRoles);
    try {
//        mailSenderService.sendMail(user.getEmail(),
//            "Registration in Imed",
//            "Registration completed successfully");
    } catch (MailException mailException) {
      logger.error("Error while sending email registration: " + mailException);
    }

    appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

    map.put(status, UserStatus.REGISTRATION_OK);
    map.put("user", appUser);
    return map;
  }

  private AppUser getUserByEmail(AppUser appUser) {
    return userRepository.findByUsername(appUser.getUsername());
  }

  private boolean isEmailValid(String email) {
    Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
    return pattern.matcher(email).matches();
  }

  private boolean isCorrectPassword(AppUser appUser) {
    return appUser.getPassword() != null && appUser.getPassword().length() >= lengthOfUserPassword;
  }

}
