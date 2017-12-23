package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import imedevo.model.User;
import imedevo.service.RegistrationService;

@RestController
@RequestMapping("/users")
public class RegistrationController {

  @Autowired
  RegistrationService registrationService;

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public Map<String, Object> userRegistration(@RequestBody User user) {
    return registrationService.createNewUserInDB(user);
  }
}
