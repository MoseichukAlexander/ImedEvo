package imedevo.service;


import imedevo.entities.RegistrationStatus;
import imedevo.model.User;
import imedevo.repository.UserRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public Map<String, Object> createNewUserInDB(User user) {
        Map<String, Object> map = new HashMap<>();
        if (getUserByEmail(user) != null) {
            map.put("status", RegistrationStatus.ERROR_DUPLICATE_USERS);
            return map;
        }

        if (user.getEmail().isEmpty()) {
            map.put("status", RegistrationStatus.ERROR_EMPTY_EMAIL);
            return map;
        }

        if (!isEmailValid(user.getEmail())) {
            map.put("status", RegistrationStatus.ERROR_INCORRECT_EMAIL);
            return map;
        }

        if (user.getPhone().isEmpty()) {
            map.put("status", RegistrationStatus.ERROR_EMPTY_PHONE);
            return map;
        }


        if (isCorrectPassword(user)) {
            userRepository.saveNewUserInDB(user);
            try {
                mailSenderService.sendMail(user.getEmail(),
                        "Registration in Imed",
                        "Registration cpmpleted succesfully");
            } catch (MailException e) {
                logger.error("Error while sending email registration: " + e);
            }

            map.put("status", RegistrationStatus.USER_REGISTRATION_OK);
            map.put("user", user);
            return map;
        }


        map.put("status", RegistrationStatus.ERROR_INCORRECT_PASSWORD);
        return map;
    }

    public User getUserByEmail(User user) {
        return userRepository.getUser(user);
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        return matches;

    }

    public boolean isCorrectPassword(User user) {
        String password = user.getPassword();
        char[] pswd = password.toCharArray();
        int password_length = pswd.length;
        if (password_length > 6) {
            return true;
        }
        return false;
    }

}
