package by.tms.school.exception.exController;

import by.tms.school.exception.InvalidInputException;
import by.tms.school.exception.userException.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class UserExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserDoNotExistsException.class)
    public ResponseEntity<String> notExist() {
        return new ResponseEntity("user does not exist", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> exist() {
        return new ResponseEntity("user already exists", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> notFound() {
        return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
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
