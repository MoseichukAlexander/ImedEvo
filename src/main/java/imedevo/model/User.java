package imedevo.model;

public class User {

  private long id;
  private String name;
  private String phone;
  private String email;
  private String password;
  private int birthDate;


  public User() {
    id = 0;
    name = "None";
    phone = "None";
    email = "None";
    password = "None";
    birthDate = 0;
  }

  public User(long id, String name, String phone, String email, String password, int birthDate) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.password = password;
    this.birthDate = birthDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public int getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(int birthDate) {
    this.birthDate = birthDate;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "{ name:" + name + ", " +
            "e-mail: " + email + " }";
  }
}