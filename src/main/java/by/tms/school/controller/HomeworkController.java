package by.tms.school.controller;

import by.tms.school.model.User;
import by.tms.school.service.HomeworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
