package by.tms.school.service;

import by.tms.school.exception.InvalidInputException;
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

    public String seeTask(long id){
        if(!homeworkRepository.findById(id).isPresent()) throw new InvalidInputException();
        String byId = homeworkRepository.findById(id).get().getTask();
        return byId;
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
        currentUser.setPoints(currentUser.getPoints()+mark);
        return "hometask checked, your mark is " + mark;
    }

}
