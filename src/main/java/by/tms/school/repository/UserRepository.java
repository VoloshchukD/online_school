package by.tms.school.repository;

import by.tms.school.model.Course;
import by.tms.school.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    @Query(value = "select u from User u order by u.points desc")
    List<User> selectUsersByPoints();

}
