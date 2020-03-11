package by.tms.school.exception.exController;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.UserDoNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CourseExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<String> notFound() {
        return new ResponseEntity("course not found", HttpStatus.NOT_FOUND);
    }

}
