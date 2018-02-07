package imedevo.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doctor_specializations")
public class DoctorSpecialization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "doctor_id")
  private long doctorId;

  @Column(name = "specialization_id")
  private long specializationId;

  public DoctorSpecialization(long doctorId, long specializationId) {
    this.doctorId = doctorId;
    this.specializationId = specializationId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(long doctorId) {
    this.doctorId = doctorId;
  }

  public long getSpecializationId() {
    return specializationId;
  }

  public void setSpecializationId(long specializationId) {
    this.specializationId = specializationId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(doctorId, specializationId);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    DoctorSpecialization anotherdoctorSpecialization = (DoctorSpecialization) obj;
    return Objects.equals(doctorId, anotherdoctorSpecialization.getDoctorId())
        && Objects.equals(specializationId, anotherdoctorSpecialization.getSpecializationId());
  }
}
