package by.tms.school.service;

import by.tms.school.exception.InvalidInputException;
import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.NoAccessException;
import by.tms.school.model.*;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.LessonRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Optional;

@Service
public class EditorService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final LessonRepository lessonRepository;

    private final UserService userService;

    private final CourseService courseService;

    private final HttpSession httpSession;

    public EditorService(UserRepository userRepository, CourseRepository courseRepository, LessonRepository lessonRepository, UserService userService, CourseService courseService, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.userService = userService;
        this.courseService = courseService;
        this.httpSession = httpSession;
    }

    public String editCourseName(long courseId, String name){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Course> byId = courseRepository.findById(courseId);
        if(!byId.isPresent()) throw new CourseNotFoundException();
        byId.get().setName(name);
        courseRepository.save(byId.get());
        return "updated";
    }

    public String addNewCourseCategory(long courseId, Category category){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Course> byId = courseRepository.findById(courseId);
        if(!byId.isPresent()) throw new CourseNotFoundException();
        if(category != null){
                    byId.get().getCategories().add(category);
        }
        courseRepository.save(byId.get());
        return "updated";
    }

    public String editLessonName(long lessonId, String name){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        byId.get().setName(name);
        lessonRepository.save(byId.get());
        return "updated";
    }

    public String editLessonLsExam(long lessonId, LessonExamination lsExam){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if(lsExam != null){
            byId.get().setLsExam(lsExam);
        }
        lessonRepository.save(byId.get());
        return "updated";
    }

    public String editLessonContent(long lessonId, File content){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if(content != null){
            byId.get().setContent(content);
        } else {
            return "no file to upd";
        }
        lessonRepository.save(byId.get());
        return "updated";
    }

    public String addLesson(Lesson lesson){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) throw new NoAccessException();
        Optional<Lesson> byId = lessonRepository.findById(lesson.getId());
        if(byId.isPresent()) throw new InvalidInputException();
        lessonRepository.save(lesson);
        return "added";
    }

    public String matchLessonAndCourse(long lessonId, long courseId){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) throw new NoAccessException();
        Optional<Lesson> byIdLsn = lessonRepository.findById(lessonId);
        Optional<Course> byIdCrs = courseRepository.findById(courseId);
        if(byIdCrs.get().getLessons().contains(byIdLsn)) throw new InvalidInputException();
        byIdCrs.get().getLessons().add(byIdLsn.get());
        courseRepository.save(byIdCrs.get());
        return "matched";
    }



}
