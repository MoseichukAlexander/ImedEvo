package imedevo.httpStatuses;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DocStatus {

  // registration status
  REGISTRATION_OK(800, "Registration success. Check your e-mail."),
  REGISTRATION_ERROR_INCORRECT_USER_ID(801, "You entered an incorrect user id"),
  REGISTRATION_ERROR_INCORRECT_DOCTOR_SPECIALIZATION(802,
      "You entered an incorrect doctor specialization"),
  REGISTRATION_ERROR_EMPTY_FIELD_DOCTOR_QUALIFICATION(803,
      "Error. Empty field  Doctor qualification"),
  REGISTRATION_ERROR_EMPTY_FIELD_DOCTOR_EDUCATION(804, "Error. Empty field Doctor education"),
  REGISTRATION_ERROR_INCORRECT_PRICE_VALUE(805, "You entered an incorrect value in price field"),
  REGISTRATION_ERROR_INCORRECT_EXPERIENCE_VALUE(806,
      "You entered an incorrect value in experience field"),
  REGISTRATION_ERROR_USER_NOT_EXIST(807, "This user does not exist"),

  // login status
  LOGIN_OK(810, "Login success."),
  DOCTOR_NOT_FOUND(811, "This Doctor profile not found."),
  LOGIN_BAD_LOGIN(812, "Login success."),
  LOGIN_BAD_PASSWORD(813, "Login success."),

  //logout status
  LOGOUT_OK(820, "Login success."),

  //edit profile status
  EDIT_DOCTOR_PROFILE_SUCCESS(830, "Your doctor profile changed successfully."),
  EDIT_DOCTOR_PROFILE_ERROR(831, "An error occurred while editing your doctor profile."),

  //comments
  ADD_COMMENT_SUCCESS(841, "Your comment was added."),
  EDIT_COMMENT_SUCCESS(843, "Your comment was edited successfully."),

  //see time
  SEE_TIME_SUCCESS(850, "You was apply your patient time."),
  SEE_TIME_REFUSE(851, "You was refuse your patient time."),

  // delete profile
  DELETE_PROFILE_SUCCESS(860, "Your profile was deleted."),

  // search status
  REQUEST_PASSED(870, "Request passed."),
  DOCTOR_PROFILE_NOT_EXIST(871, "This Doctor profile not exist."),
  NOT_SPECIFIED_PARAMETERS(872, "Search parameter not specified");

  private int code;
  private String message;


  DocStatus(int code, String message) {
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
