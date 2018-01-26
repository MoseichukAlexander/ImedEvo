package imedevo.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import imedevo.httpStatuses.UserStatus;
import imedevo.model.Role;
import imedevo.model.User;
import imedevo.model.UserRole;
import imedevo.repository.UserRepository;
import javax.transaction.Transactional;

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

  @Transactional
  public Map<String, Object> createNewUserInDB(User user) {
    Map<String, Object> map = new HashMap<>();

    if (getUserByEmail(user) != null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_DUPLICATE_USERS);
      return map;
    }

    if (user.getEmail() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_EMAIL);
      return map;
    }

    if (!isEmailValid(user.getEmail())) {
      map.put(status, UserStatus.REGISTRATION_ERROR_INCORRECT_EMAIL);
      return map;
    }

    if (user.getPhone() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_PHONE);
      return map;
    }

    if (user.getPhone().length() != 13 || user.getPhone().charAt(0) != '+') {
      map.put(status, UserStatus.REGISTRATION_ERROR_PHONE_INVALID);
      return map;
    }

    if (user.getFirstName() == null || user.getLastName() == null
        || user.getFirstName().length() < 2 || user.getLastName().length() < 2) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_FIRSTNAME_OR_LASTNAME);
      return map;
    }

    if (user.getBirthDate() == null) {
      map.put(status, UserStatus.REGISTRATION_ERROR_EMPTY_BIRTHADAY);
      return map;
    }

    if (user.getBirthDate().toLocalDate().isBefore(LocalDate.now().minusYears(100)) ||
        user.getBirthDate().toLocalDate().isAfter(LocalDate.now())) {
      map.put(status, UserStatus.REGISTRATION_ERROR_BIRTHDAY_INVALID);
      return map;
    }

    if (!isCorrectPassword(user)) {
      map.put(status, UserStatus.REGISTRATION_ERROR_INCORRECT_PASSWORD);
      return map;
    }

    userRepository.save(user);
    List<UserRole> userRoles = rolesService.getUserRoles(user.getId());
    userRoles.add(new UserRole(user.getId(), Role.USER));
    rolesService.save(userRoles);
    try {
//        mailSenderService.sendMail(user.getEmail(),
//            "Registration in Imed",
//            "Registration completed successfully");
    } catch (MailException mailException) {
      logger.error("Error while sending email registration: " + mailException);
    }

    map.put(status, UserStatus.REGISTRATION_OK);
    map.put("user", user);
    return map;
  }

  private User getUserByEmail(User user) {
    return userRepository.findByEmail(user.getEmail());
  }

  private boolean isEmailValid(String email) {
    Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
    return pattern.matcher(email).matches();
  }

  private boolean isCorrectPassword(User user) {
    return user.getPassword() != null && user.getPassword().length() >= lengthOfUserPassword;
  }
}
