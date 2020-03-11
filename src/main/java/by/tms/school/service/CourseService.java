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
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(1,123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        courseRepository.save(course);
        Course byName = courseRepository.findByName(name);
        if(byName.equals(null)) throw new UserNotFoundException();
        return byName;
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

    public String study(String courseName){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getCourses().contains(courseRepository.findByName(courseName))){
            Course course = courseRepository.findByName(courseName);
            List<Lesson> lessons = course.getLessons();
            for(int i = 0; i < lessons.size(); i++){
                Lesson lesson = lessons.get(i);
                if(lesson.getLsCondition().equals(Lesson.Condition.IN_PROCESS) ||
                        lesson.getLsCondition()==null){
                    lesson.setLsCondition(Lesson.Condition.DONE);
                    if(lesson.getLsExam().getExamResult().equals(LessonExamination.Status.NOT_DONE) ||
                            lesson.getLsExam().getExamResult() == null){
                        course.setLessons(lessons);
                        user.getCourses().remove(course);
                        user.getCourses().add(course);
                        userRepository.save(user);
                        return ("make a test to lesson №"+(i+1));
                    }
                    return "all is DONE";
                }
            }
        }
        throw new CourseNotFoundException();
    }

    public String passExam(String courseName, int answer){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getCourses().contains(courseRepository.findByName(courseName))){
            Course course = courseRepository.findByName(courseName);
            List<Lesson> lessons = course.getLessons();
            for(int i = 0; i < lessons.size(); i++){
                Lesson lesson = lessons.get(i);
                if( lesson.getLsCondition().equals(Lesson.Condition.DONE) ){
                    if(lesson.getLsExam().getExamResult().equals(LessonExamination.Status.NOT_DONE) ||
                            lesson.getLsExam().getExamResult() == null){
                        if(lesson.getLsExam().getAnswer() == answer){
                            lesson.getLsExam().setExamResult(LessonExamination.Status.DONE);
                            course.setLessons(lessons);
                            user.getCourses().remove(course);
                            user.getCourses().add(course);
                            userRepository.save(user);
                            return ("test is DONE");
                        }
                    }
                    return "all is DONE";
                }
            }
        }
        throw new CourseNotFoundException();
    }

    public String addCourseAdmin(Course course){
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getUsername().equals("ADMIN") && user.getId()==0
                && user.getPassword().equals("qwertyuiop123321")){
            courseRepository.save(course);
            return "added";
        }
        throw new NoRootsUserException();
    }

}
