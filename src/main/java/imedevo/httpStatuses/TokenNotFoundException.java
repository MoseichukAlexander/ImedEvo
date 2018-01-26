package imedevo.httpStatuses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This token is invalid!")
public class TokenNotFoundException extends Throwable {

}