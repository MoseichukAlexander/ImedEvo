package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserNotFoundException;
import imedevo.model.User;
import imedevo.service.UserService;
import org.springframework.web.multipart.MultipartFile;

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

  @PutMapping("/{id}")
  public Map<String, Object> updateUser(@RequestBody User user) {
    return userService.updateUser(user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable long id)
      throws UserNotFoundException, AccessDeniedException {
    userService.delete(id);
  }
}