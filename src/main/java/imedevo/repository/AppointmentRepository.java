package imedevo.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import imedevo.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

  List<Appointment> findByUserId(long userId);

  List<Appointment> findByDoctorId(long doctorId);
}
