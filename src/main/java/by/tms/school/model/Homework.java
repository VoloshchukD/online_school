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
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private File task;
    private String q1;
    private String q2;
    private String q3;
    private Mark mark;

    public enum Mark{
        ONE, TWO, THREE, NO_MARK
    }

}
