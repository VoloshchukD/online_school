package by.tms.school.service;

import by.tms.school.model.Course;
import by.tms.school.model.User;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
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

    public List<User> selectUsersByPoints(){
        return userRepository.selectUsersByPoints();
    }

    public List<Course> selectCouresByRating(){
        return courseRepository.selectCoursesByRating();
    }

}
