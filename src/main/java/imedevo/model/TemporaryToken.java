package imedevo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "temporary_tokens")
public class TemporaryToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "token")
  private String token;

  @Column(name = "user_email")
  private String userEmail;

  @Column(name = "expiration_date")
  private LocalDateTime expirationDate;

  public TemporaryToken() {
  }

  public TemporaryToken(String userEmail) {
    this.token = UUID.randomUUID().toString();
    this.userEmail = userEmail;
    this.expirationDate = LocalDateTime.now().plusHours(24);
  }

  public long getId() {
    return id;
  }

  public String getToken() {
    return token;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  @Override
  public String toString() {
    return "TemporaryToken{" +
        "id=" + id +
        ", token='" + token + '\'' +
        ", userEmail='" + userEmail + '\'' +
        ", expirationDate=" + expirationDate +
        '}';
  }
}
