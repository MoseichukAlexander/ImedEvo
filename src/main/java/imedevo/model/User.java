package imedevo.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "patronymic")
  private String patronymic;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "city")
  private String city;

  @Column(name = "house")
  private String house;

  @Column(name = "street")
  private String street;

  @Column(name = "sex")
  private String sex;

  @Column(name = "birth_date")
  private Date birthDate;

  @Column(name = "date_of_registration")
  private LocalDateTime dateOfRegistration;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<UserRole> userRoles;

  @OneToOne
  @PrimaryKeyJoinColumn
  Doctor doctor;

  public User() {
  }

  public User(String firstName, String lastName, String phone, String email,
      String password, String city, String house, String street, String patronymic, String sex,
      Date birthDate, Doctor doctor, LocalDateTime dateOfRegistration) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.patronymic = patronymic;
    this.phone = phone;
    this.email = email;
    this.password = password;
    this.city = city;
    this.house = house;
    this.street = street;
    this.patronymic = patronymic;
    this.sex = sex;
    this.birthDate = birthDate;
    this.doctor = doctor;
    this.dateOfRegistration = dateOfRegistration;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", lastName='" + lastName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", patronymic='" + patronymic + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", city='" + city + '\'' +
        ", house='" + house + '\'' +
        ", street='" + street + '\'' +
        ", sex='" + sex + '\'' +
        ", birthDate=" + birthDate +
        ", dateOfRegistration=" + dateOfRegistration +
        ", userRoles=" + userRoles +
        ", doctor=" + doctor +
        '}';
  }

  public long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getHouse() {
    return house;
  }

  public void setHouse(String house) {
    this.house = house;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public List<UserRole> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(List<UserRole> userRoles) {
    this.userRoles = userRoles;
  }

  public LocalDateTime getDateOfRegistration() {
    return dateOfRegistration;
  }

  public void setDateOfRegistration(LocalDateTime dateOfRegistration) {
    this.dateOfRegistration = dateOfRegistration;
  }
}