package by.tms.school.service;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.NotAuthorizedUserException;
import by.tms.school.exception.userException.UserNotFoundException;
import by.tms.school.model.*;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    private final LessonService lessonService;

    private Map<String, List<Integer> > rating = new HashMap<>();

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository, HttpSession httpSession, LessonService lessonService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
        this.lessonService = lessonService;
    }

    public Course findCourse(String name){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Course byName = courseRepository.findByName(name);
        if(byName == null) throw new UserNotFoundException();
        return byName;
    }

    public List<Course> findCoursesByCategory(Category category){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        List<Course> byCategory = courseRepository.findCoursesByCategories(category);
        if(byCategory == null) throw new CourseNotFoundException();
        return byCategory;
    }

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

    public String finishCourse(String courseName){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Course byName = courseRepository.findByName(courseName);
        if(byName == null) throw new CourseNotFoundException();
        User currentUser = (User) httpSession.getAttribute("currentUser");
        List<Course> courses = currentUser.getCourses();
        Map<String, Integer> progress = lessonService.getProgress();
        if (progress.get(currentUser.getId()+courseName)+1
                == courseRepository.findByName(courseName).getLessons().size()) {
            currentUser.setPoints(currentUser.getPoints()+10);
            userRepository.save(currentUser);
            return "you finished course";
        }
        return "study all lessons first";
    }

    public String rateCourse(String courseName, int mark){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Course byName = courseRepository.findByName(courseName);
        int markToSet = 0;
        int sum = 0;
        if(byName == null) throw new CourseNotFoundException();
        if(rating.containsKey(courseName)){
            List<Integer> marks = rating.get(courseName);
            marks.add(mark);
            for(int m : marks){
                sum += m;
            }
            rating.put(courseName,marks);
            markToSet = ((sum)/(marks.size()));
        } else{
            List<Integer> marksf = new ArrayList<>();
            marksf.add(mark);
            rating.put(courseName,marksf);
            markToSet = mark;
        }
        byName.setRating(markToSet);
        courseRepository.save(byName);
        return "thank you for your rate";
    }

}
