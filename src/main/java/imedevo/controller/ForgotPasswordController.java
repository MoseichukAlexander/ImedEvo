package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import imedevo.httpStatuses.NoSuchUserException;
import imedevo.service.ForgotPasswordService;


@RestController
@RequestMapping("/forgot")
public class ForgotPasswordController {

  @Autowired
  private ForgotPasswordService forgotPasswordService;

  @PostMapping(value = "/reset")
  public Map<String, Object> resetPassword(@RequestParam(name = "email") String email)
      throws NoSuchUserException {
    return forgotPasswordService.resetPassword(email);
  }

  @PostMapping(value = "/newpassword")
  public Map<String, Object> settingnewpassword(@RequestParam(name = "token") String token,
      @RequestParam(name = "newpassword") String newPassword) {
    return forgotPasswordService.settingNewPassword(token, newPassword);
  }
}
