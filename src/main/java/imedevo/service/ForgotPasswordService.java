package imedevo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import imedevo.httpStatuses.NoSuchUserException;
import imedevo.httpStatuses.TokenStatus;
import imedevo.model.AppUser;
import imedevo.model.TemporaryToken;
import imedevo.repository.TemporaryTokenRepository;
import imedevo.repository.UserRepository;
import javax.transaction.Transactional;

@Service
public class ForgotPasswordService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailSenderService mailSender;

  @Autowired
  private TemporaryTokenRepository tokenRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public Map<String, Object> resetPassword(String username) throws NoSuchUserException {
    Map<String, Object> map = new HashMap<>();
    if (userRepository.findByUsername(username) != null) {
      TemporaryToken token = new TemporaryToken(username);
      tokenRepository.save(token);
      sendResetTokenEmail(token);
      map.put("status", TokenStatus.NEW_TOKEN_CREATED);
      return map;
    } else {
      throw new NoSuchUserException();
    }
  }

  private void sendResetTokenEmail(TemporaryToken token) {
    mailSender.sendMail(token.getUserEmail(), "Сброс пароля iMED",
        "Мы узнали, что ваш пароль был утерян..."
            + "\n\nНо не стоит беспокоиться! Вы можете использовать ссылку в течении 24 часов для его восстановления:"
            + "\nhttp://www.imed.od.ua/forgot/resetpassword/" + token.getToken()
            + "\n\nЕсли ссылка не будет использована в указанный срок - она будет аннулирована."
            + "\n\nС уважением,\nкоманда проекта iMED");
  }

  @Transactional
  public Map<String, Object> settingNewPassword(String token, String newPassword) {
    Map<String, Object> map = new HashMap<>();
    TemporaryToken tempToken = tokenRepository.findByToken(token);
    if (tempToken != null) {
      AppUser user = userRepository.findByUsername(tempToken.getUserEmail());
      if (tempToken.getExpirationDate().isAfter(LocalDateTime.now())) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        tokenRepository.delete(tempToken.getId());
        userRepository.save(user);
        map.put("status", TokenStatus.PASSWORD_CHANGED);
      } else {
        map.put("status", TokenStatus.TIME_EXPIRED);
      }
    } else {
      map.put("status", TokenStatus.TOKEN_NOT_FOUND);
    }
    return map;
  }

}
