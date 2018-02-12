package imedevo.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favourite_doctors")
public class FavouriteDoctor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "doctor_id")
  private long doctorId;

  @Column(name = "date_of_entry")
  private LocalDateTime dateOfEntry;

  public FavouriteDoctor() {
  }

  public FavouriteDoctor(long id, long userId, long doctorId, LocalDateTime dateOfEntry) {
    this.id = id;
    this.userId = userId;
    this.doctorId = doctorId;
    this.dateOfEntry = dateOfEntry;
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

  public LocalDateTime getDateOfEntry() {
    return dateOfEntry;
  }

  public void setDateOfEntry(LocalDateTime dateOfEntry) {
    this.dateOfEntry = dateOfEntry;
  }

  @Override
  public String toString() {
    return "FavouriteDoctor{" +
        "id=" + id +
        ", userId=" + userId +
        ", doctorId=" + doctorId +
        ", dateOfEntry=" + dateOfEntry +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FavouriteDoctor that = (FavouriteDoctor) o;
    return userId == that.userId &&
        doctorId == that.doctorId &&
        Objects.equals(dateOfEntry, that.dateOfEntry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, doctorId, dateOfEntry);
  }
}
