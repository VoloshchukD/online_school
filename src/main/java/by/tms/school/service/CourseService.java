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
import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    private final LessonService lessonService;

    private Map<String, List<Integer> > rating = new HashMap<>();

    private Map<String, List<Course> > finishedCourses = new HashMap<>();

    public Map<String, List<Integer>> getRating() {
        return rating;
    }

    public Map<String, List<Course>> getFinishedCourses() {
        return finishedCourses;
    }

    public void setFinishedCourses(Map<String, List<Course>> finishedCourses) {
        this.finishedCourses = finishedCourses;
    }

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
        User user1 = (User) userRepository.findById(user.getId()).get();
        List<Course> courses = user.getCourses();
        courses.remove(byName);
        user1.getCourses().remove(byName);
        userRepository.save(user1);
        user.setCourses(courses);
        userRepository.save(user);
        return "successfully leaved";
    }

    public String finishCourse(long id){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Optional<Course> byId = courseRepository.findById(id);
        if(!byId.isPresent()) throw new CourseNotFoundException();
        User currentUser = (User) httpSession.getAttribute("currentUser");
        Map<String, Integer> progressLesson = lessonService.getProgressLesson();
        Map<String, Integer> progressExam = lessonService.getProgressLsExam();
        if (progressLesson.get(currentUser.getId()+byId.get().getName())+1
                == courseRepository.findById(id).get().getLessons().size()) {
            if (progressExam.get(currentUser.getId()+byId.get().getName())+1
                    == courseRepository.findById(id).get().getLessons().size()) {
                currentUser.setPoints(currentUser.getPoints()+10);
                User user1 = userRepository.findById(currentUser.getId()).get();
                List<Course> coursesUser = currentUser.getCourses();
                List<Course> coursesToSet = currentUser.getCourses();
                for(Course course1 : coursesUser){
                    if(course1.getId() == id) continue;
                    coursesToSet.add(course1);
                }
//                user1.getCourses().remove(byId.get());
//                userRepository.save(user1);
                currentUser.setCourses(coursesToSet);
                user1.setCourses(coursesToSet);
                userRepository.save(user1);
                userRepository.save(currentUser);
                List<Course> list;
                if(finishedCourses.containsKey(currentUser.getUsername())){
                     list = finishedCourses.get(currentUser.getUsername());
                } else{
                     list = new ArrayList();
            }
            list.add(byId.get());
            finishedCourses.put(currentUser.getUsername(),list);
            return "you finished course";
            }
            return "pass all exams first";
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
