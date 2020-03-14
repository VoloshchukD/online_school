package by.tms.school.service;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.NotAuthorizedUserException;
import by.tms.school.model.Course;
import by.tms.school.model.Lesson;
import by.tms.school.model.LessonExamination;
import by.tms.school.model.User;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.LessonRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    @Autowired
    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, UserRepository userRepository, HttpSession httpSession) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
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

}
