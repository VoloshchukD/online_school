package by.tms.school.exception.exController;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.NoAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EditorExceptionController {

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<String> notFound() {
        return new ResponseEntity("you have no access", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
