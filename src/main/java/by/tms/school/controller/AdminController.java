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

    @GetMapping(path = "/urs")
    public List<User> findAll(){
        return adminService.showAllAdmin();
    }

    @PutMapping(path = "/upd")
    public User updateUser(@RequestParam @Min(1) long id, @RequestParam @NotNull String username, @RequestParam @NotNull String password){
        return adminService.updateUsersProfileAdmin(id,username,password);
    }

    @DeleteMapping(path = "/dlt")
    public String deleteUser(@RequestParam @Min(1) long id){
        return adminService.deleteUsersProfileAdmin(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addCourseAdmin(@RequestBody @NotNull Course course){
        return new ResponseEntity(adminService.addCourseAdmin(course), HttpStatus.OK);
    }

}
