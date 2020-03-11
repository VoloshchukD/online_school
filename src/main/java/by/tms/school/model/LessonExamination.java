package by.tms.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int answer;
    private Status examResult;

    public enum Status{
        DONE, NOT_DONE
    }

}
