package by.tms.school.controller;

import by.tms.school.model.Category;
import by.tms.school.model.Course;
import by.tms.school.model.User;
import by.tms.school.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/course")
@Validated
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/find")
    public ResponseEntity<Course> findByUsername(@RequestParam @NotNull String name){
        return new ResponseEntity(courseService.findCourse(name), HttpStatus.OK);
    }

//    @GetMapping(path = "/findByCategory")
//    public ResponseEntity<List> findByCategory(@RequestParam @NotNull String categoryName){
//        return new ResponseEntity(courseService.findCoursesByCategory(categoryName), HttpStatus.OK);
//    }

    @GetMapping(path = "/showAll")
    public ResponseEntity<List> showAll(){
        return new ResponseEntity(courseService.showAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/enter")
    public ResponseEntity<String> enterCourse(@RequestParam @NotNull String name){
        return new ResponseEntity(courseService.enterCourse(name), HttpStatus.OK);
    }

    @PostMapping(path = "/leave")
    public ResponseEntity<String> leaveCourse(@RequestParam @NotNull String name){
        return new ResponseEntity(courseService.leaveCourse(name), HttpStatus.OK);
    }

}
