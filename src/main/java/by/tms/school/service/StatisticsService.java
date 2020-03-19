package by.tms.school.service;

import by.tms.school.model.User;
import by.tms.school.repository.CourseRepository;
import by.tms.school.repository.UserRepository;
import by.tms.school.model.comparator.UserByPointsComparator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public StatisticsService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<User> getRating(){
        List<User> users = userRepository.findAll();
        users.sort(new UserByPointsComparator().reversed());
        return users;
    }


}
