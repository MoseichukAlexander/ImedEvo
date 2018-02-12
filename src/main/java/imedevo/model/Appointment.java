package imedevo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "doctor_id")
  private long doctorId;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "is_approved")
  private boolean isApproved;

  @Column(name = "status")
  private String status;

  public Appointment(long id, long userId, long doctorId, LocalDateTime startTime,
      boolean isAproved, String status) {
    this.id = id;
    this.userId = userId;
    this.doctorId = doctorId;
    this.startTime = startTime;
    this.isApproved = isAproved;
    this.status = status;
  }

  public Appointment() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getUserId() {
    return userId;
  }

  public long getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(long doctorId) {
    this.doctorId = doctorId;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public boolean isApproved() {
    return isApproved;
  }

  public void setApproved(boolean approved) {
    isApproved = approved;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Appointment{" +
        "id=" + id +
        ", userId=" + userId +
        ", doctorId=" + doctorId +
        ", startTime=" + startTime +
        ", isApproved=" + isApproved +
        '}';
  }
}
