package imedevo.model;

public enum Role {
  USER(1),
  DOCTOR(2),
  CLINIC_ADMIN(3),
  SUPER_ADMIN(4),
  SYSTEM(5),
  ANONYMOUS(6),
  BLOGGER(7);

  private long id;

  Role(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public static Role createRole(long id) {
    switch ((int) id) {
      case 1:
        return USER;
      case 2:
        return DOCTOR;
      case 3:
        return CLINIC_ADMIN;
      case 4:
        return SUPER_ADMIN;
      case 5:
        return SYSTEM;
      case 7:
        return BLOGGER;
      default:
        return ANONYMOUS;
    }
  }
}