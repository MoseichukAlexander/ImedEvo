package imedevo.httpStatuses;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TokenStatus {
  NEW_TOKEN_CREATED(50, "Вам отправлено письмо на электронную почту, Перейдите по этой ссылке для смены пароля!"),
  TOKEN_INVALID(51, "Token is invalid"),
  TOKEN_NOT_FOUND(52, "Token not found"),
  PASSWORD_CHANGED(53, "Password changed!"),
  TIME_EXPIRED(54,"The token expired");

  private int code;
  private String message;

  TokenStatus(int code, String message) {
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
    return "TokenStatus{" +
        "code=" + code +
        ", message='" + message + '\'' +
        '}';
  }
}
