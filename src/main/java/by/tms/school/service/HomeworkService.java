package by.tms.school.service;

import by.tms.school.model.Homework;
import by.tms.school.model.User;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.HomeworkRepository;
import by.tms.school.repository.LessonRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Optional;

@Service
public class HomeworkService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final HomeworkRepository homeworkRepository;

    private final LessonRepository lessonRepository;

    private final HttpSession httpSession;

    public HomeworkService(UserRepository userRepository, CourseRepository courseRepository, HomeworkRepository homeworkRepository, LessonRepository lessonRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.homeworkRepository = homeworkRepository;
        this.lessonRepository = lessonRepository;
        this.httpSession = httpSession;
    }

    public File seeTask(long id){
        Optional<Homework> byId = homeworkRepository.findById(id);
        return byId.get().getTask();
    }

    public String checkHometask(long id, String answer1, String answer2, String answer3){
        Optional<Homework> byId = homeworkRepository.findById(id);
        User currentUser = (User) httpSession.getAttribute("currentUser");
        int mark = 0;
        if(byId.get().getAnswer1().equals(answer1)){
            mark++;
        }
        if(byId.get().getAnswer2().equals(answer2)){
            mark++;
        }
        if(byId.get().getAnswer3().equals(answer3)){
            mark++;
        }
//        currentUser.setPoints(currentUser.getPoints()+byId.get().getMark());
        homeworkRepository.save(byId.get());
        return "hometask checked, your mark is " + mark;
    }

}
