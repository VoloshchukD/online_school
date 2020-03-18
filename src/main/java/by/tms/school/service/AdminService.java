package by.tms.school.service;

import by.tms.school.exception.userException.NoRootsUserException;
import by.tms.school.exception.userException.UserNotFoundException;
import by.tms.school.model.*;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final HttpSession httpSession;

    public AdminService(CourseRepository courseRepository, UserRepository userRepository, UserService userService, HttpSession httpSession) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    public String initAction(){
        User user = new User();
        user.setId(1);
        user.setUsername("Editor1");
        user.setPassword("qwerty123");
        List<User> editors = new ArrayList<>();
        editors.add(user);
        userService.setEditors(editors);
        userRepository.save(user);
        List<Lesson> lessons = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("language"));
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(1, "",123456, LessonExamination.Status.NOT_DONE),
                new Homework(1,new File("hometask.txt"),"a","b","c", 0),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", categories, lessons);
        courseRepository.save(course);
        return "done";
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

    public List<User> showAllAdmin(){
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getUsername().equals("ADMIN") && user.getId()==0
                && user.getPassword().equals("qwertyuiop123321")){
            return userRepository.findAll();
        }
        throw new NoRootsUserException();
    }

    public String deleteUsersProfileAdmin(long id){
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getUsername().equals("ADMIN") && user.getId()==0
                && user.getPassword().equals("qwertyuiop123321")){
            Optional<User> byId = userRepository.findById(id);
            if(!byId.isPresent()) throw new UserNotFoundException();
            userRepository.deleteById(id);
            return "deleted";
        }
        throw new NoRootsUserException();
    }

    public User updateUsersProfileAdmin(long id, String username, String password){
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getUsername().equals("ADMIN") && user.getId()==0
                && user.getPassword().equals("qwertyuiop123321")){
            Optional<User> byId = userRepository.findById(id);
            if(!byId.isPresent()) throw new UserNotFoundException();
            byId.get().setUsername(username);
            byId.get().setPassword(password);
            userRepository.save(byId.get());
            return byId.get();
        }
        throw new NoRootsUserException();
    }

}
