package by.tms.school.controller;

import by.tms.school.model.Course;
import by.tms.school.model.User;
import by.tms.school.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/find")
    public ResponseEntity<Course> findByUsername(@RequestParam String name){
        return new ResponseEntity(courseService.findCourse(name), HttpStatus.OK);
    }

    @PostMapping(path = "/show")
    public ResponseEntity<List> showAll(){
        return new ResponseEntity(courseService.showAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/enter")
    public ResponseEntity<String> enterCourse(@RequestParam String name){
        return new ResponseEntity(courseService.enterCourse(name), HttpStatus.OK);
    }

    @PostMapping(path = "/leave")
    public ResponseEntity<String> leaveCourse(@RequestParam String name){
        return new ResponseEntity(courseService.leaveCourse(name), HttpStatus.OK);
    }

    @PostMapping(path = "/study")
    public ResponseEntity<String> study(@RequestParam String courseName){
        return new ResponseEntity(courseService.study(courseName), HttpStatus.OK);
    }

    @PostMapping(path = "/passExam")
    public ResponseEntity<String> passExam(@RequestParam String courseName, @RequestParam int answer){
        return new ResponseEntity(courseService.passExam(courseName, answer), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<String> addCourseAdmin(@RequestBody Course course){
        return new ResponseEntity(courseService.addCourseAdmin(course), HttpStatus.OK);
    }

}
