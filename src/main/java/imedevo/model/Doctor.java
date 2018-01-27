package imedevo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Model (@link Doctor) class.
 */

@Entity
@Table(name = "doctors")
public class Doctor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "user_id")
  private long userId;

  @OneToOne(mappedBy = "doctor")
  private User user;

  @Column(name = "doctor_gualification")
  private String doctorGualification;
  @Column(name = "education")
  private String education;
  @Column(name = "doctor_achievements")
  private String doctorAchievements;
  @Column(name = "price")
  private int price;
  @Column(name = "work_experience")
  private int workExperience;
  @Column(name = "pediatrician")
  private boolean pediatrician;
  @Column(name = "rating")
  private int reting;

  public Doctor() {
  }

  public Doctor(long userId, User user, String doctorGualification, String education,
      String doctorAchievements, int price, int workExperience, boolean pediatrician, int reting) {
    this.userId = userId;
    this.user = user;
    this.doctorGualification = doctorGualification;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getDoctorGualification() {
    return doctorGualification;
  }

  public void setDoctorGualification(String doctorGualification) {
    this.doctorGualification = doctorGualification;
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

  public int getReting() {
    return reting;
  }

  public void setReting(int reting) {
    this.reting = reting;
  }
}
