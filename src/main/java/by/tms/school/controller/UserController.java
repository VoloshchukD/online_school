package by.tms.school.controller;

import by.tms.school.model.User;
import by.tms.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(path = "/reg")
    public ResponseEntity<String> regUser(@RequestBody User user){
        return new ResponseEntity(userService.reg(user), HttpStatus.OK);
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<String> authUser(@RequestParam String username, @RequestParam String password){
        return new ResponseEntity(userService.auth(username, password), HttpStatus.OK);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logoutUser(){
        return new ResponseEntity(userService.logout(), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<User> findByUsername(@RequestParam String username){
        return new ResponseEntity(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> delete(@RequestParam long id,@RequestParam String password){
        return new ResponseEntity(userService.deleteUserProfile(id,password), HttpStatus.OK);
    }

    @PostMapping(path = "/myprofile")
    public ResponseEntity<User> show(){
        return new ResponseEntity(userService.showProfile(), HttpStatus.OK);
    }

    @GetMapping(path = "/urs")
    public List<User> findAll(){
        return userService.showAllAdmin();
    }

    @PostMapping(path = "/upd")
    public User updateUser(@RequestParam long id, @RequestParam String username, @RequestParam String password){
        return userService.updateUsersProfileAdmin(id,username,password);
    }

    @DeleteMapping(path = "/dlt")
    public String deleteUser(@RequestParam long id){
        return userService.deleteUsersProfileAdmin(id);
    }

}
