package by.tms.school.controller;

import by.tms.school.model.User;
import by.tms.school.service.HomeworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homework")
@Validated
public class HomeworkController {

    private final HomeworkService homeworkService;

    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @GetMapping(path = "/seeTask")
    public ResponseEntity<User> seeTask(@RequestParam long id){
        return new ResponseEntity(homeworkService.seeTask(id), HttpStatus.OK);
    }

    @PostMapping(path = "/checkHometask/{hmtskId}")
    public ResponseEntity<User> checkHometask(@PathVariable("hmtskId") long id, String answer1, String answer2, String answer3){
        return new ResponseEntity(homeworkService.checkHometask(id, answer1, answer2, answer3), HttpStatus.OK);
    }

}