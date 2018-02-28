package imedevo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Model (@link Doctor) class.
 */

@Entity
@Table(name = "doctors")
public class Doctor {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @OneToOne
  @PrimaryKeyJoinColumn
  private AppUser user;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "doctor_specializations",
      joinColumns = @JoinColumn(name = "doctor_id"),
      inverseJoinColumns = @JoinColumn(name = "specialization_id"))
  private List<Specialization> specialization;

  @Column(name = "doctor_gualification")
  private String doctorQualification;
  @Column(name = "education")
  private String education;
  @Column(name = "doctor_achievements")
  private String doctorAchievements;
  @Column(name = "price")
  private Integer price;
  @Column(name = "work_experience")
  private Integer workExperience;
  @Column(name = "pediatrician")
  private boolean pediatrician;
  @Column(name = "rating")
  private Double reting;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "doctor_clinics",
      joinColumns = @JoinColumn(name = "doctor_id"),
      inverseJoinColumns = @JoinColumn(name = "clinic_id"))
  private List<DoctorClinic> doctorClinics;

  public Doctor() {

  }

  public Doctor(long userId, AppUser user,
      String doctorQualification, String education, String doctorAchievements, int price,
      int workExperience, boolean pediatrician, Double reting) {
    this.userId = userId;
    this.user = user;
    this.specialization = new ArrayList<>();
    this.doctorQualification = doctorQualification;
    this.education = education;
    this.doctorAchievements = doctorAchievements;
    this.price = price;
    this.workExperience = workExperience;
    this.pediatrician = pediatrician;
    this.reting = reting;
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

  public AppUser getUser() {
    return user;
  }

  public void setUser(AppUser AppUser) {
    this.user = AppUser;
  }

  public List<Specialization> getSpecialization() {
    return specialization;
  }

  public void setSpecialization(List<Specialization> specialization) {
    this.specialization = specialization;
  }

  public String getDoctorQualification() {
    return doctorQualification;
  }

  public void setDoctorQualification(String doctorQualification) {
    this.doctorQualification = doctorQualification;
  }

  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public String getDoctorAchievements() {
    return doctorAchievements;
  }

  public void setDoctorAchievements(String doctorAchievements) {
    this.doctorAchievements = doctorAchievements;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getWorkExperience() {
    return workExperience;
  }

  public void setWorkExperience(int workExperience) {
    this.workExperience = workExperience;
  }

  public boolean isPediatrician() {
    return pediatrician;
  }

  public void setPediatrician(boolean pediatrician) {
    this.pediatrician = pediatrician;
  }

  public Double getReting() {
    return reting;
  }

  public void setReting(Double reting) {
    this.reting = reting;
  }

  public List<DoctorClinic> getDoctorClinics() {
    return doctorClinics;
  }

  public void setDoctorClinics(List<DoctorClinic> doctorClinics) {
    this.doctorClinics = doctorClinics;
  }
}
