package imedevo.controller;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.AppUser;
import imedevo.model.ChangePassword;
import imedevo.service.UserService;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/getall")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public List<AppUser> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN', 'BLOGGER')")
  public AppUser getUserById(@PathVariable long id) throws UserNotFoundException {
    return userService.getById(id);
  }

  @PostMapping("/createuser")
  public Map<String, Object> createUser(@RequestBody AppUser user) {
    return userService.save(user);
  }

  @PostMapping("/uploaduserimage")
  @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN', 'BLOGGER')")
  public Map<String, Object> uploadUserImage(@RequestParam("user_id") long userId,
      @RequestParam("file") MultipartFile imageFile) {
    return userService.uploadImage(userId, imageFile);
  }

  @PutMapping("/updateuser")
  @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN', 'BLOGGER')")
  public Map<String, Object> updateUser(@RequestBody AppUser appUser) throws UserNotFoundException {
    return userService.updateUser(appUser);
  }

  @DeleteMapping("/deleteuser/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
  public void deleteUser(@PathVariable long id)
      throws UserNotFoundException, AccessDeniedException {
    userService.deleteUser(id);
  }

  @PostMapping(value = "/login")
  public Map<String, Object> login(@RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password) throws ServletException {
    return userService.login(email, password);
  }

  @PostMapping("/changepassword")
  @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'CLINIC_ADMIN', 'SUPER_ADMIN', 'BLOGGER')")
  public Map<String, Object> changePassword(@RequestBody ChangePassword changePassword)
      throws AccessDeniedException {
    return userService.changePassword(changePassword);
  }
}