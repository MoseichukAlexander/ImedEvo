package imedevo.httpStatuses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with such email already exists")
public class UserAlreadyExistsException extends RuntimeException {

}
