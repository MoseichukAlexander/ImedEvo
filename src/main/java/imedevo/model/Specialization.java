package imedevo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Specialization for (@link Doctor) class.
 */

@Entity
@Table(name = "specializations")
public class Specialization {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long specializationId;

  @Column(name = "specialization_ru")
  private String specializationName;

  @Column(name = "specialization_en")
  private String specializationNameEn;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "doctor_specializations",
      joinColumns = @JoinColumn(name = "specialization_id"),
      inverseJoinColumns = @JoinColumn(name = "doctor_id"))
  @JsonIgnore
  private List<Doctor> doctors;

  public Specialization() {
    this.specializationName = "None";
    this.specializationNameEn = "None";
  }

  public Specialization(String specializationName, String specializationNameEn) {
    this.specializationName = specializationName;
    this.specializationNameEn = specializationNameEn;
  }

  public long getSpecializationId() {
    return specializationId;
  }

  public void setSpecializationId(long specializationId) {
    this.specializationId = specializationId;
  }

  public void setSpecializationName(String specializationName) {
    this.specializationName = specializationName;
  }

  public String getSpecializationNameEn() {
    return specializationNameEn;
  }

  public void setSpecializationNameEn(String specializationNameEn) {
    this.specializationNameEn = specializationNameEn;
  }

  public List<Doctor> getDoctors() {
    return doctors;
  }

  public void setDoctors(List<Doctor> doctors) {
    this.doctors = doctors;
  }

  @Override
  public int hashCode() {
    return Objects.hash(specializationName, specializationNameEn);
  }

  @Override
  public String toString() {
    return "Specialization{"
        + "id=" + specializationId
        + "specialization, ='" + specializationName + '\''
        + ", specializationEn='" + specializationNameEn + '\''
        + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    Specialization anotherSpecialization = (Specialization) obj;
    return Objects.equals(specializationName, anotherSpecialization.specializationName)
        && Objects.equals(specializationNameEn, anotherSpecialization.specializationNameEn);
  }
}