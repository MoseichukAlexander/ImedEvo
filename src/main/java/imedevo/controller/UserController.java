package imedevo.controller;

import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.User;
import imedevo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/getall")
  public List<User> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable long id) throws UserNotFoundException {
    return userService.getById(id);
  }

  @PostMapping("/createuser")
  public Map<String, Object> createUser(@RequestBody User user) {
    return userService.save(user);
  }

  @PostMapping("/uploaduserimage")
  public Map<String, Object> uploadUserImage(@RequestParam("user_id") long userId,
                                             @RequestParam("file") MultipartFile imageFile){
    return userService.uploadImage(userId, imageFile);
  }

  @PutMapping("/updateuser")

  public Map<String, Object> updateUser(@RequestBody User user) {
    return userService.updateUser(user);
  }

  @DeleteMapping("/deleteuser/{id}")
  public void deleteUser(@PathVariable long id)
      throws UserNotFoundException, AccessDeniedException {
    userService.delete(id);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Map<String, Object> login(@RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password) {
    return userService.login(email, password);
  }
}