package by.tms.school.repository;

import by.tms.school.model.Category;
import by.tms.school.model.Course;
import by.tms.school.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByName(String name);

    List<Course> findCoursesByCategories(Category category);

    @Query(value = "select c from Course c order by c.rating desc")
    List<Course> selectCoursesByRating();


}
