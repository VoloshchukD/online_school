package by.tms.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private File content;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LessonExamination lsExam;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Homework homework;
}
