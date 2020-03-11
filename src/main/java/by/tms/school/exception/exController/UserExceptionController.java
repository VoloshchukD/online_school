package by.tms.school.exception.exController;

import by.tms.school.exception.InvalidInputException;
import by.tms.school.exception.userException.NoRootsUserException;
import by.tms.school.exception.userException.NotAuthorizedUserException;
import by.tms.school.exception.userException.UserDoNotExistsException;
import by.tms.school.exception.userException.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserDoNotExistsException.class)
    public ResponseEntity<String> notExist() {
        return new ResponseEntity("user already exists", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> notFound() {
        return new ResponseEntity("user do not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> notValid() {
        return new ResponseEntity("input data is not valid", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotAuthorizedUserException.class)
    public ResponseEntity<String> noAuthorization() {
        return new ResponseEntity("you are not authorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoRootsUserException.class)
    public ResponseEntity<String> noRoots() {
        return new ResponseEntity("you are not admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
