package by.tms.school.service;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.lessonException.NotAllowedOrderException;
import by.tms.school.exception.userException.NotAuthorizedUserException;
import by.tms.school.model.Course;
import by.tms.school.model.Lesson;
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

    private Map<String, Integer> progressLesson = new HashMap<>();

    private Map<String, Integer> progressExam = new HashMap<>();

    public Map<String, Integer> getProgressLesson() {
        return progressLesson;
    }

    public Map<String, Integer> getProgressLsExam() {
        return progressExam;
    }

    public void setProgressLesson(Map<String, Integer> progressLesson) {
        this.progressLesson = progressLesson;
    }

    public void setProgressExam(Map<String, Integer> progressExam) {
        this.progressExam = progressExam;
    }

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
            if(progressLesson.containsKey(currentUser.getId()+courseName)){
                if(lessonNumber != (progressLesson.get(currentUser.getId()+courseName)+1)){
                    throw new NotAllowedOrderException();
                }
            }
            currentUser.setPoints(currentUser.getPoints()+1);
            progressLesson.put(currentUser.getId()+courseName,lessonNumber);
            userRepository.save(currentUser);
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
            if(progressLesson.get(currentUser.getId()+courseName) == lessonNumber){
                if(lesson.getLsExam().getAnswer() == answer){
                    currentUser.setPoints(currentUser.getPoints()+5);
                    userRepository.save(currentUser);
                    progressExam.put(currentUser.getId()+courseName,lessonNumber);
                    return "you passed exam";
                } else {
                    return "you did not pass the exam";
                }
            } else{
                return "lesson is not done";
            }
        }
        throw new CourseNotFoundException();
    }

    public String showCourseProgress(String courseName){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if(currentUser.getCourses().contains(courseRepository.findByName(courseName))) {
            Course course = courseRepository.findByName(courseName);
            int lsProgress = progressLesson.get(currentUser.getId()+courseName)+1;
            int ExamProgress = progressExam.get(currentUser.getId()+courseName)+1;

            return lsProgress+" lsn passed \n"+ExamProgress+" exm done";
        }
        throw new CourseNotFoundException();
    }

}
