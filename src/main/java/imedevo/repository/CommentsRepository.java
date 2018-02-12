package imedevo.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import imedevo.model.Comment;

public interface CommentsRepository extends CrudRepository<Comment, Long> {

  List<Comment> findByUserId(long userId);

  List<Comment> findByDoctorId(long doctorId);

  List<Comment> findByClinicId(long clinicId);
}
