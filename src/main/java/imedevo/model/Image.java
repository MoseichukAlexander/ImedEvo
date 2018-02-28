package imedevo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_images")
public class Image {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "image_link")
  private String link;

  public Image(long userId, String link) {
    this.userId = userId;
    this.link = link;
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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
