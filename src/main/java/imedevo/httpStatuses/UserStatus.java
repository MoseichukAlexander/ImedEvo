package imedevo.httpStatuses;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus {

    // registration status
    REGISTRATION_OK(700, "Registration success"),
    REGISTRATION_ERROR_INCORRECT_PASSWORD(701, "Password invalid"),
    REGISTRATION_ERROR_DUPLICATE_USERS(702, "User with such e-mail already exist"),
    REGISTRATION_ERROR_EMPTY_EMAIL(703, "Empty e-mail"),
    REGISTRATION_ERROR_INCORRECT_EMAIL(704, "E-mail invalid"),
    REGISTRATION_ERROR_EMPTY_PHONE(705, "Empty phone"),
    REGISTRATION_ERROR_EMPTY_BIRTHADAY(706, "Empty birthday"),
    REGISTRATION_ERROR_EMPTY_FIRSTNAME_OR_LASTNAME(707, "First or last name are empty"),
    REGISTRATION_ERROR_BIRTHDAY_INVALID(708, "Birthday invalid"),
    REGISTRATION_ERROR_PHONE_INVALID(709, "Phone invalid"),

    // all
    ACCESS_DENIED(403, "Permission to perform this action is absent. Access not granted"),
    NOT_FOUND(404, "User not found"),

    // login status
    LOGIN_OK(710, "Login success"),
    LOGIN_USER_NOT_FOUND(711, "User with such credentials not found"),
    LOGIN_BAD_LOGIN(712, "Login invalid"),
    LOGIN_BAD_PASSWORD(713, "Password invalid"),

    //logout status
    LOGOUT_OK(720, "Logout success"),

    //edit profile status
    EDIT_PROFILE_SUCCESS(730, "Your profile updated"),
    EDIT_PROFILE_ERROR(731, "Error while editing profile"),
    ADD_USER_OK(732, "Adding user in DataBase OK"),

    // comments and  rating
    ADD_COMMENT_SUCCESS(741, "Comment added"),
    ADD_RATING_SUCCESS(742, "Rating added"),
    EDIT_COMMENT_SUCCESS(743, "Comment edited"),
    EDIT_RATING_SUCCESS(744, "Rating edited"),

    //sign up for an appointment with the doctor,
    SEE_DOC_SUCCESS(750, "Doctor approved your visit time"),
    SEE_DOC_REFUSE(751, "Doctor cancelled your visit time"),

    //upload user photo
    IMAGE_IS_EMPTY(760, "Your image is empty."),
    IMAGE_UPLOAD_SUCCESS(761, "You uploading success"),

    // deleting profile
    DELETE_PROFILE_SUCCESS(790, "Profile deleted");

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