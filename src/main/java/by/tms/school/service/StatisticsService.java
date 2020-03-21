package by.tms.school.service;

import by.tms.school.model.User;
import by.tms.school.model.comparator.UserByEndedCoursesComparator;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
import by.tms.school.model.comparator.UserByPointsComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final UserRepository userRepository;

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    @Autowired
    public StatisticsService(UserRepository userRepository, CourseService courseService, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    public List<User> getRating(){
        List<User> users = userRepository.findAll();
        users.sort(new UserByPointsComparator().reversed());
        return users;
    }

    public List<User> getUsersByEndedCourses(){
        List<User> users = userRepository.findAll();
        users.sort(new UserByEndedCoursesComparator(courseService).reversed());
        return users;
    }

}
