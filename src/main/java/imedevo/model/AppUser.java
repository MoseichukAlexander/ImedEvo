package imedevo.model;

import java.sql.Date;
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
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "patronymic")
  private String patronymic;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String username;

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
  private String dateOfRegistration;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<UserRole> userRoles;

  @OneToOne
  @PrimaryKeyJoinColumn
  private Image image;

  public AppUser() {
  }

  public AppUser(String firstName, String lastName, String phone, String username,
      String password, String city, String house, String street, String patronymic, String sex,
      Date birthDate, String dateOfRegistration, Image image) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.patronymic = patronymic;
    this.phone = phone;
    this.username = username;
    this.password = password;
    this.city = city;
    this.house = house;
    this.street = street;
    this.patronymic = patronymic;
    this.sex = sex;
    this.birthDate = birthDate;
    this.dateOfRegistration = dateOfRegistration;
    this.image = image;
  }

  @Override
  public String toString() {
    return "{" +
        "id=" + id +
        ", lastName='" + lastName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", patronymic='" + patronymic + '\'' +
        ", phone='" + phone + '\'' +
        ", username='" + username + '\'' +
        ", city='" + city + '\'' +
        ", house='" + house + '\'' +
        ", street='" + street + '\'' +
        ", sex='" + sex + '\'' +
        ", birthDate=" + birthDate +
        ", dateOfRegistration=" + dateOfRegistration +
        ", userRoles=" + userRoles +
        ", image=" + image +
        '}';
  }

  public Long getId() {
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public String getDateOfRegistration() {
    return dateOfRegistration;
  }

  public void setDateOfRegistration(String dateOfRegistration) {
    this.dateOfRegistration = dateOfRegistration;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }
}