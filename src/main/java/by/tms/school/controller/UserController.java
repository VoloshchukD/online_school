package by.tms.school.controller;

import by.tms.school.model.User;
import by.tms.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/reg")
    public ResponseEntity<String> regUser(@RequestBody @NotNull User user){
        return new ResponseEntity(userService.reg(user), HttpStatus.OK);
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<String> authUser(@RequestParam @NotNull String username, @RequestParam @NotNull String password){
        return new ResponseEntity(userService.auth(username, password), HttpStatus.OK);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logoutUser(){
        return new ResponseEntity(userService.logout(), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<User> findByUsername(@RequestParam @NotNull String username){
        return new ResponseEntity(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable("userId") @Min(1) long id,
                                         @RequestParam @NotNull String password){
        return new ResponseEntity(userService.deleteUserProfile(id,password), HttpStatus.OK);
    }

    @GetMapping(path = "/myprofile")
    public ResponseEntity<User> show(){
        return new ResponseEntity(userService.showProfile(), HttpStatus.OK);
    }

}
