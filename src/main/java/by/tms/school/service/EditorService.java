package by.tms.school.service;

import by.tms.school.exception.courseException.CourseNotFoundException;
import by.tms.school.exception.userException.NoAccessException;
import by.tms.school.model.*;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.LessonRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
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

    public String addCourseCategories(long courseId, List<Category> categoryList){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Course> byId = courseRepository.findById(courseId);
        if(!byId.isPresent()) throw new CourseNotFoundException();
        if(categoryList != null){
            for(Category category : categoryList){
                if(!byId.get().getCategories().contains(category))
                    byId.get().getCategories().add(category);
            }
        }
        courseRepository.save(byId.get());
        return "updated";
    }

    public String editLesson(long lessonId, String name, LessonExamination lsExam, File content){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
//        if(!byId.isPresent()) throw new LessonNotFoundException();
        byId.get().setName(name);
        if(lsExam != null){
           byId.get().setLsExam(lsExam);
        }
        if(content != null){
            byId.get().setContent(content);
        }
        lessonRepository.save(byId.get());
        return "updated";
    }

    public String editLessonExamination(long lessonExamId, String questions, int answer){
        User user = (User) httpSession.getAttribute("currentUser");
        if(!userService.getEditors().contains(user)) {
            throw new NoAccessException();
        }
        Lesson lesson = lessonRepository.findLessonByLsExam_IdContaining(lessonExamId);
        LessonExamination lsExam = lesson.getLsExam();
//        if(!byId.isPresent()) throw new CourseNotFoundException();
        lsExam.setQuestions(questions);
        lsExam.setAnswer(answer);
        lesson.setLsExam(lsExam);
        lessonRepository.save(lesson);
        return "updated";
    }

}
