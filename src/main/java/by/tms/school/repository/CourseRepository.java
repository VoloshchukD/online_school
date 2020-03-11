package by.tms.school.repository;

import by.tms.school.model.Course;
import by.tms.school.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByName(String name);



}
