package imedevo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "doctor_id")
  private long doctorId;

  @Column(name = "clinic_id")
  private long clinicId;

  @Column(name = "text")
  private String text;

  @Column(name = "date_time")
  private LocalDateTime dateTime;

  public Comment() {
  }

  public Comment(long id, long userId, long doctorId, long clinicId, String text,
      LocalDateTime dateTime) {
    this.id = id;
    this.userId = userId;
    this.doctorId = doctorId;
    this.clinicId = clinicId;
    this.text = text;
    this.dateTime = dateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(long doctorId) {
    this.doctorId = doctorId;
  }

  public long getClinicId() {
    return clinicId;
  }

  public void setClinicId(long clinicId) {
    this.clinicId = clinicId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "Comment{" +
        "id=" + id +
        ", userId=" + userId +
        ", doctorId=" + doctorId +
        ", clinicId=" + clinicId +
        ", text='" + text + '\'' +
        ", dateTime=" + dateTime +
        '}';
  }
}
