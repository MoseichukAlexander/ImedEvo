package imedevo.controller;

import imedevo.model.AppUser;
import imedevo.service.RegistrationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class RegistrationController {

  @Autowired
  RegistrationService registrationService;


  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public Map<String, Object> userRegistration(@RequestBody AppUser appUser) {
    return registrationService.createNewUserInDB(appUser);
  }
}
