package imedevo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imedevo.httpStatuses.AccessDeniedException;
import imedevo.httpStatuses.UserStatus;
import imedevo.model.Comment;
import imedevo.repository.CommentsRepository;
import imedevo.repository.UserRepository;

@Service
public class CommentService {

  @Autowired
  private CommentsRepository commentsRepository;

  @Autowired
  private UserRepository userRepository;

  Map<String, Object> map = new HashMap<>();

  public List<Comment> getByUserId(long id) {
    return commentsRepository.findByUserId(id);
  }

  public List<Comment> getByDoctorId(long id) {
    return commentsRepository.findByDoctorId(id);
  }

  public List<Comment> getByClinicId(long id) {
    return commentsRepository.findByClinicId(id);
  }

  public Map<String, Object> addComment(Comment comment) {

    if (comment == null) {
      map.put("status", UserStatus.COMMENT_INVALID);
      return map;
    }
    comment.setDateTime(LocalDateTime.now());
    map.put("status", UserStatus.ADD_COMMENT_SUCCESS);
    map.put("comment", commentsRepository.save(comment));
    return map;
  }

  public Map<String, Object> updateComment(Comment updatedComment)
      throws AccessDeniedException {
    /** this is security checking */
//    if (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != updatedComment.getUserId()
//        || userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != updatedComment.getDoctorId()) {
//      throw new AccessDeniedException();
//    }

    Map<String, Object> map = new HashMap<>();

    Comment commentFromDb = commentsRepository.findOne(updatedComment.getId());
    if (commentFromDb == null) {
      map.put("status", UserStatus.COMMENT_NOT_FOUND);
    } else {
      Field[] fields = updatedComment.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        Object appointmentFromDbValue = ReflectionUtils.getField(field, updatedComment);
        if (appointmentFromDbValue != null) {
          ReflectionUtils.setField(field, commentFromDb, appointmentFromDbValue);
        }
      }
      map.put("status", UserStatus.EDIT_COMMENT_SUCCESS);
      map.put("comment", commentsRepository.save(commentFromDb));
    }
    return map;
  }

  public Map<String, Object> deleteComment(long id) throws AccessDeniedException {
    /** this is security checking */
//    if (userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != commentsRepository.findOne(id).getUserId()
//        || userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
//        .getId() != commentsRepository.findOne(id).getDoctorId()) {
//      throw new AccessDeniedException();
//    }

    Map<String, Object> map = new HashMap<>();
    if (commentsRepository.findOne(id) != null) {
      commentsRepository.delete(id);
      map.put("status", UserStatus.DELETE_COMMENT);
    } else {
      map.put("status", UserStatus.COMMENT_NOT_FOUND);
    }
    return map;
  }
}
