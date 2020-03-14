package by.tms.school;

import by.tms.school.model.Course;
import by.tms.school.model.Lesson;
import by.tms.school.model.LessonExamination;
import by.tms.school.model.User;
import by.tms.school.service.AdminService;
import by.tms.school.service.CourseService;
import by.tms.school.service.LessonService;
import by.tms.school.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SchoolApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    LessonService lessonService;

    @Autowired
    AdminService adminService;

    @Test
    void reg() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        assertEquals("you successfully reged", userService.reg(user));
    }

    @Test
    void auth() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        assertEquals("you successfully authorized", userService.auth("Jerry","abc123"));
    }

    @Test
    void log() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals("you logged out", userService.logout());
    }

    @Test
    void findByUsername() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals("Jerry", userService.findUserByUsername("Jerry").getUsername());
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals("your profile successfully deleted", userService.deleteUserProfile(1,"abc123"));
    }

    @Test
    void showProfile() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals("Jerry", userService.showProfile().getUsername());
    }

    @Test
    void showAllAdmin() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        userService.logout();
        userService.auth("ADMIN","qwertyuiop123321");
        assertTrue(adminService.showAllAdmin().size() == 1);
    }

    @Test
    void deleteUserAdmin() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        userService.logout();
        userService.auth("ADMIN","qwertyuiop123321");
        assertEquals("deleted",adminService.deleteUsersProfileAdmin(1));
    }

    @Test
    void updateUserAdmin() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        userService.logout();
        userService.auth("ADMIN","qwertyuiop123321");
        assertEquals("Tom",adminService.updateUsersProfileAdmin(1,"Tom","123").getUsername());
    }

    @Test
    void findCourse() {
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals("english",courseService.findCourse("english").getName());
    }

    @Test
    void findAllCourses() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "",123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        adminService.addCourseAdmin(course);
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals(course.getName(),courseService.findCourse("english").getName());
    }

    @Test
    void showAllCourses() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "",123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        adminService.addCourseAdmin(course);
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertTrue(courseService.showAll().size() == 1);
    }

    @Test
    void enterCourse() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "", 123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        adminService.addCourseAdmin(course);
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        assertEquals("successfully entered", courseService.enterCourse("english"));
    }

    @Test
    void leaveCourse() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "", 123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        adminService.addCourseAdmin(course);
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        courseService.enterCourse("english");
        assertEquals("successfully leaved",courseService.leaveCourse("english"));
    }

    @Test
    void studyCourse() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "", 123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        adminService.addCourseAdmin(course);
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        courseService.enterCourse("english");
        assertEquals("make a test to lesson №1",lessonService.study("english"));
    }

    @Test
    void passExam() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "", 123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        adminService.addCourseAdmin(course);
        User user = new User();
        user.setPassword("abc123");
        user.setUsername("Jerry");
        user.setAge(33);
        userService.reg(user);
        userService.auth("Jerry","abc123");
        courseService.enterCourse("english");
        lessonService.study("english");
        assertEquals("test is DONE",lessonService.passExam("english",123456));
    }

    @Test
    void addCourseAdmin() {
        userService.auth("ADMIN","qwertyuiop123321");
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1,"lesson_1",null, new LessonExamination(
                1, "", 123456, LessonExamination.Status.NOT_DONE),Lesson.Condition.IN_PROCESS));
        Course course = new Course(1,"english", null, lessons);
        assertEquals("added",adminService.addCourseAdmin(course));
    }

}
