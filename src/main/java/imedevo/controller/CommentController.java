package imedevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.model.Comment;
import imedevo.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/byusers/{id}")
  public List<Comment> getByUserId(@PathVariable long id) {
    return commentService.getByUserId(id);
  }

  @GetMapping("bydoctors/{id}")
  public List<Comment> getByDoctorId(@PathVariable long id) {
    return commentService.getByDoctorId(id);
  }

  @GetMapping("byclinics/{id}")
  public List<Comment> getByClinicId(@PathVariable long id) {
    return commentService.getByClinicId(id);
  }

  @PostMapping("/addcomment")
  public Map<String, Object> addComment(@RequestBody Comment comment) {
    return commentService.addComment(comment);
  }

  @PutMapping("/updatecomment")
  public Map<String, Object> updateComment(@RequestBody Comment comment)
      throws AccessDeniedException {
    return commentService.updateComment(comment);
  }

  @DeleteMapping("/deletecomment")
  public Map<String, Object> deleteComment(@RequestParam(name = "id") long id)
      throws AccessDeniedException {
    return commentService.deleteComment(id);
  }
}
