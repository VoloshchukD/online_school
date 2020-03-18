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
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    private Map<String, Integer> progress = new HashMap<>();

    @Autowired
    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, UserRepository userRepository, HttpSession httpSession) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    public File study(String courseName, int lessonNumber){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if(currentUser.getCourses().contains(courseRepository.findByName(courseName))){
            Course course = courseRepository.findByName(courseName);
            List<Lesson> lessons = course.getLessons();
            Lesson lesson = lessons.get(lessonNumber);
            if(progress.containsKey(currentUser.getId()+courseName)){
                if(lessonNumber != progress.get(currentUser.getId()+courseName)){
                    return null;
                }
            }

            progress.put(currentUser.getId()+courseName,lessonNumber);
            return lesson.getContent();
        }
        throw new CourseNotFoundException();
    }

    public String passExam(String courseName, int lessonNumber, int answer){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if(currentUser.getCourses().contains(courseRepository.findByName(courseName))) {
            Course course = courseRepository.findByName(courseName);
            List<Lesson> lessons = course.getLessons();
            Lesson lesson = lessons.get(lessonNumber);
            if(lesson.getLsExam().getAnswer() == answer)
                currentUser.setPoints(currentUser.getPoints()+5);
                return "you passed exam";
        }
        throw new CourseNotFoundException();
    }

}
