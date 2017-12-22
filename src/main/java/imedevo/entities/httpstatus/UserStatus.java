package imedevo.entities.httpstatus;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus {

    // registration status
    REGISTRATION_OK(700, "Registration success. Check your e-mail."),
    REGISTRATION_ERROR_INCORRECT_PASSWORD(701, "You entered an incorrect password"),
    REGISTRATION_ERROR_DUPLICATE_USERS(702, "User with such e-mail already exist"),
    REGISTRATION_ERROR_EMPTY_EMAIL(703, "Empty e-mail"),
    REGISTRATION_ERROR_INCORRECT_EMAIL(704, "You enter incorrect e-mail"),
    REGISTRATION_ERROR_EMPTY_PHONE(705, "Error. Empty phone."),
    REGISTRATION_ERROR_EMPTY_BIRTHADAY(706, "Error. Empty Birthday."),
    REGISTRATION_ERROR_EMPTY_NAME(707, "Error. Empty name."),

    // login status
    LOGIN_OK(710, "Login success."),
    LOGIN_USER_NOT_FOUND(711, "User with your credentials not found."),
    LOGIN_BAD_LOGIN(712, "Login success."),
    LOGIN_BAD_PASSWORD(713, "Login success."),


    //logout status
    LOGOUT_OK(720, "Logout success."),


    //edit profile status
    EDIT_PROFILE_SUCCESS(730, "Your profile changed successfully."),
    EDIT_PROFILE_ERROR(731, "An error occurred while editing your profile."),

    // comments and  rating
    ADD_COMMENT_SUCCESS(741, "Your comment was added."),
    ADD_RATING_SUCCESS(742, "Your rating was added."),
    EDIT_COMMENT_SUCCESS(743, "Your comment was edited successfully."),
    EDIT_RATING_SUCCESS(744, "Your rating was edited successfully."),

    //sign up for an appointment with the doctor,
    SEE_DOC_SUCCESS(750, "Doctor was apply your visit time."),
    SEE_DOC_REFUSE(751, "Doctor was refuse your visit time."),

    // deleting profile
    DELETE_PROFILE_SUCCESS(760, "Your profile was deleted.")
    ;

    private int code;

    private String message;

    UserStatus(int code, String message) {
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
