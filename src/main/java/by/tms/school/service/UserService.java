package by.tms.school.service;

import by.tms.school.exception.InvalidInputException;
import by.tms.school.exception.userException.NotAuthorizedUserException;
import by.tms.school.exception.userException.UserNotFoundException;
import by.tms.school.model.User;
import by.tms.school.repository.UserRepository;
import by.tms.school.exception.userException.UserDoNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.tms.school.model.User.Status.OFFLINE;
import static by.tms.school.model.User.Status.ONLINE;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    private List<User> editors = new ArrayList<>();

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }

    @Autowired
    public UserService(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    public String reg(User user){
        if(userRepository.existsById(user.getId())) throw new UserDoNotExistsException();
        user.setCourses(new ArrayList<>());
        userRepository.save(user);
        return "you successfully reged";
    }

    public String auth(String username, String password){
        if(username.equals("ADMIN") && password.equals("qwertyuiop123321")){
            User admin = new User();
            admin.setId(0);
            admin.setUsername("ADMIN");
            admin.setPassword("qwertyuiop123321");
            httpSession.setAttribute("currentUser", admin);
            return "ADMIN MODE ON";
        }
        User byUsername = userRepository.findUserByUsername(username);
        if(byUsername == null) throw new UserNotFoundException();
        if(!byUsername.getPassword().equals(password)) throw new InvalidInputException();
        byUsername.setUserStatus(ONLINE);
        httpSession.setAttribute("currentUser", byUsername);
        userRepository.save(byUsername);
        if(editors.contains(byUsername)){
            return "you authorized as editor";
        }
        return "you successfully authorized";
    }

    public String logout(){
        User user = (User) httpSession.getAttribute("currentUser");
        if(user.getUsername().equals("ADMIN") && user.getId()==0
                && user.getPassword().equals("qwertyuiop123321")){
            httpSession.removeAttribute("currentUser");
            return "ADMIN MODE OFF";
        }
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        if(user.getUserStatus().equals(OFFLINE)) throw new InvalidInputException();
        user.setUserStatus(OFFLINE);
        httpSession.removeAttribute("currentUser");
        userRepository.save(user);
        return "you logged out";
    }

    public User findUserByUsername(String username){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        User byUsername = userRepository.findUserByUsername(username);
        if(byUsername.equals(null)) throw new UserNotFoundException();
        return byUsername;
    }

    public String deleteUserProfile(long id, String password){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        Optional<User> byId = userRepository.findById(id);
        if(byId.get().getId() != id || !byId.get().getPassword().equals(password)) throw new InvalidInputException();
        if(!byId.isPresent()) throw new UserNotFoundException();
        userRepository.deleteById(id);
        return "your profile successfully deleted";
    }

    public User showProfile(){
        if(httpSession.getAttribute("currentUser")==null) throw new NotAuthorizedUserException();
        return (User) httpSession.getAttribute("currentUser");
    }



}
