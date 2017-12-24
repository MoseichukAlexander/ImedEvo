package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import imedevo.model.User;
import imedevo.service.UserService;

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
  public User getUserById(@PathVariable long id) {
    return userService.getById(id);
  }

  @PostMapping("/createuser")
  public User createUser(@RequestBody User user) {
    return userService.save(user);
  }

  @PutMapping("/{id}")
  public User updateUser(@RequestBody User user) {
    return userService.updateUser(user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable long id) {
    userService.delete(id);
  }
}