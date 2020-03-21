package by.tms.school.model.comparator;

import by.tms.school.model.User;
import by.tms.school.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

public class UserByEndedCoursesComparator implements Comparator<User> {

    private final CourseService courseService;

    @Autowired
    public UserByEndedCoursesComparator(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public int compare(User o1, User o2) {
        if (!courseService.getFinishedCourses().containsKey(o2) &&
                !courseService.getFinishedCourses().containsKey(o1)) {
            return 0;
        }
        if (!courseService.getFinishedCourses().containsKey(o2) &&
                courseService.getFinishedCourses().containsKey(o1)) {
            return courseService.getFinishedCourses().get(o1.getUsername()).size();
        }
        if (!courseService.getFinishedCourses().containsKey(o1) &&
                courseService.getFinishedCourses().containsKey(o2)) {
            return -courseService.getFinishedCourses().get(o2.getUsername()).size();
        }
        return courseService.getFinishedCourses().get(o1.getUsername()).size()-
                courseService.getFinishedCourses().get(o2.getUsername()).size();
    }

}
