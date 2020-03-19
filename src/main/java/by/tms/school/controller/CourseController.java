package by.tms.school.controller;

import by.tms.school.model.Category;
import by.tms.school.model.Course;
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

    @GetMapping(path = "/findByCategory")
    public List findByCategory(@RequestBody @NotNull Category category){
        return courseService.findCoursesByCategory(category);
    }

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

    @PostMapping(path = "/finish")
    public ResponseEntity<String> finishCourse(@RequestParam @NotNull String name){
        return new ResponseEntity(courseService.finishCourse(name), HttpStatus.OK);
    }

    @PostMapping(path = "/rate")
    public ResponseEntity<String> rateCourse(@RequestParam @NotNull String name, @RequestParam int mark){
        return new ResponseEntity(courseService.rateCourse(name, mark), HttpStatus.OK);
    }

}
