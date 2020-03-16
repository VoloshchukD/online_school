package by.tms.school.repository;

import by.tms.school.model.Homework;
import by.tms.school.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

}
