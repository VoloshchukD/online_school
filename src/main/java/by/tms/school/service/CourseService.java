package by.tms.school.service;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.NoRootsUserException;
import by.tms.school.exception.userException.NotAuthorizedUserException;
import by.tms.school.exception.userException.UserNotFoundException;
import by.tms.school.model.*;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository, HttpSession httpSession) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    public Course findCourse(String name){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        List<Lesson> lessons = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("language"));
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(1, "",123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", categories, lessons);
        courseRepository.save(course);
        Course byName = courseRepository.findByName(name);
        if(byName == null) throw new UserNotFoundException();
        return byName;
    }

//    public List<Course> findCoursesByCategory(String categoryName){
//        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
//        Category category = new Category(categoryName);
//        List<Course> byCategory = courseRepository.findCoursesByCategories(category);
//        if(byCategory == null) throw new CourseNotFoundException();
//        return byCategory;
//    }

    public List<Course> showAll(){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        return courseRepository.findAll();
    }

    public String enterCourse(String name){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Course byName = courseRepository.findByName(name);
        if(byName == null) throw new CourseNotFoundException();
        User user = (User) httpSession.getAttribute("currentUser");
        user.getCourses().add(byName);
        userRepository.save(user);
        return "successfully entered";
    }

    public String leaveCourse(String name){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Course byName = courseRepository.findByName(name);
        if(byName == null) throw new CourseNotFoundException();
        User user = (User) httpSession.getAttribute("currentUser");
        List<Course> courses = user.getCourses();
        courses.remove(byName);
        user.setCourses(courses);
        userRepository.save(user);
        return "successfully leaved";
    }





}
