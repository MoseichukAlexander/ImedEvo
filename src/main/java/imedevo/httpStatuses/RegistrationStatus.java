package imedevo.httpStatuses;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RegistrationStatus {

  USER_REGISTRATION_OK(0, "Registration success. Check your e-mail."),
  ERROR_INCORRECT_PASSWORD(1, "You entered an incorrect password"),
  ERROR_DUPLICATE_USERS(2, "User with such e-mail already exist"),
  ERROR_EMPTY_EMAIL(3, "Empty e-mail"),
  ERROR_INCORRECT_EMAIL(4, "You enter incorrect e-mail"),
  ERROR_EMPTY_PHONE(5, "Error. Empty phone."),
  ERROR_EMPTY_BIRTHADAY(6, "Error. Empty Birthday."),
  ERROR_EMPTY_NAME(7, "Error. Empty name.");

  private int code;
  private String message;


  RegistrationStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "{" +
        "code=" + code +
        ", message='" + message + '\'' +
        '}';
  }
}
