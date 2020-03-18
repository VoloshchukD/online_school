package by.tms.school.controller;

import by.tms.school.model.Course;
import by.tms.school.model.User;
import by.tms.school.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(path = "/init")
    public String initAction(){
        return adminService.initAction();
    }

    @GetMapping(path = "/showurs")
    public List<User> findAll(){
        return adminService.showAllUsers();
    }

    @PutMapping(path = "/updusr")
    public User updateUser(@RequestParam @Min(1) long id, @RequestParam @NotNull String username, @RequestParam @NotNull String password){
        return adminService.updateUserProfile(id,username,password);
    }

    @DeleteMapping(path = "/dltusr")
    public String deleteUser(@RequestParam @Min(1) long id){
        return adminService.deleteUserProfile(id);
    }

    @PostMapping(path = "/addcrs")
    public ResponseEntity<String> addCourseAdmin(@RequestBody @NotNull Course course){
        return new ResponseEntity(adminService.addCourse(course), HttpStatus.OK);
    }

    @PostMapping(path = "/addedtr")
    public ResponseEntity<String> addEditor(@RequestBody @NotNull User editor){
        return new ResponseEntity(adminService.addEditor(editor), HttpStatus.OK);
    }

    @GetMapping(path = "/showedtrs")
    public List<User> showEditors(){
        return adminService.showAllEditors();
    }

}
