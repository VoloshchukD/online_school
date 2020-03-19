package by.tms.school.exception.exController;

import by.tms.school.exception.lessonException.LessonNotFoundException;
import by.tms.school.exception.lessonException.NotAllowedOrderException;
import by.tms.school.exception.userException.NoAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LessonExceptionController {

    @ExceptionHandler(LessonNotFoundException.class)
    public ResponseEntity<String> notFound() {
        return new ResponseEntity("lesson not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAllowedOrderException.class)
    public ResponseEntity<String> notAllowedOrder() {
        return new ResponseEntity("you are trying to study lessons in wrong order", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
