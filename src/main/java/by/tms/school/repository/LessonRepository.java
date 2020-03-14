package by.tms.school.repository;

import by.tms.school.model.Course;
import by.tms.school.model.Lesson;
import by.tms.school.model.LessonExamination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Lesson findLessonByLsExam_IdContaining(long lsExam_id);

}
