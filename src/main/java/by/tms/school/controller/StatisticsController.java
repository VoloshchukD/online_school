package by.tms.school.controller;

import by.tms.school.model.User;
import by.tms.school.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(path = "/crsbyrating")
    public ResponseEntity<User> show(){
        return new ResponseEntity(statisticsService.selectCouresByRating(), HttpStatus.OK);
    }

    @GetMapping(path = "/ursbypoints")
    public ResponseEntity<User> show2(){
        return new ResponseEntity(statisticsService.selectUsersByPoints(), HttpStatus.OK);
    }

}
