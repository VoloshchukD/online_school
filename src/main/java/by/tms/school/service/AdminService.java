package by.tms.school.service;

import by.tms.school.exception.userException.NoRootsUserException;
import by.tms.school.exception.userException.UserNotFoundException;
import by.tms.school.model.Course;
import by.tms.school.model.User;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    public AdminService(CourseRepository courseRepository, UserRepository userRepository, HttpSession httpSession) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
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
