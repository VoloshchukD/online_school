package by.tms.school.controller;

import by.tms.school.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/lesson")
@Validated
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping(path = "/study")
    public ResponseEntity<String> study(@RequestParam @NotNull String courseName){
        return new ResponseEntity(lessonService.study(courseName), HttpStatus.OK);
    }

    @PostMapping(path = "/passExam")
    public ResponseEntity<String> passExam(@RequestParam @NotNull String courseName, @RequestParam int answer){
        return new ResponseEntity(lessonService.passExam(courseName, answer), HttpStatus.OK);
    }

}

