package imedevo.httpStatuses;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HospitalStatus {
  NOT_FOUND(404, "Clinic not found"),

  // registration status
  REGISTRATION_OK(900, "Registration success"),
  REGISTRATION_ERROR_INCORRECT_PASSWORD(901, "You entered an incorrect password"),
  REGISTRATION_ERROR_DUPLICATE_USERS(902, "User with such e-mail already exist"),
  REGISTRATION_ERROR_EMPTY_EMAIL(903, "Empty e-mail"),
  REGISTRATION_ERROR_INCORRECT_EMAIL(904, "Error. Email invalid"),
  REGISTRATION_ERROR_EMPTY_PHONE(905, "Error. Empty phone."),
  REGISTRATION_ERROR_EMPTY_DESCRIPTION(906, "Error. Empty Description."),
  REGISTRATION_ERROR_EMPTY_NAME(907, "Error. Empty name."),
  REGISTRATION_ERROR_EMPTY_LOGO(908, "Error. Empty logo."),
  REGISTRATION_ERROR_EMPTY_MEDICAL_LECENSE(909, "Error. Empty medical license."),
  REGISTRATION_ERROR_EMPTY_ADDRESS(910, "Error. Empty address."),
  REGISTRATION_ERROR_EMPTY_SERVICES(914, "Error. Empty services"),

  // login status
  LOGIN_OK(910, "Login success."),
  LOGIN_USER_NOT_FOUND(911, "User with your credentials not found."),
  LOGIN_BAD_LOGIN(912, "Login success."),
  LOGIN_BAD_PASSWORD(913, "Login success."),


  //logout status
  LOGOUT_OK(920, "Logout success."),


  //edit profile status
  EDIT_PROFILE_SUCCESS(930, "Your profile changed successfully."),
  EDIT_PROFILE_ERROR(931, "An error occurred while editing your profile."),

  // comments and  rating
  ADD_COMMENT_SUCCESS(941, "Your comment was added."),
  ADD_RATING_SUCCESS(942, "Your rating was added."),
  EDIT_COMMENT_SUCCESS(943, "Your comment was edited successfully."),
  EDIT_RATING_SUCCESS(944, "Your rating was edited successfully."),

  //sign up for an appointment with the doctor,
  ADD_DOC_SUCCESS(950, "Doctor was added to the base."),
  ADD_DOC_FALSE(951, "Doctor wasn't added to the base."),

  // deleting profile
  DELETE_PROFILE_SUCCESS(960, "Your profile was deleted."),
  REGISTRATION_ERROR_INCORRECT_PHONE(961, " incorrect phone");

  private int code;

  private String message;

  HospitalStatus(int code, String message) {
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
