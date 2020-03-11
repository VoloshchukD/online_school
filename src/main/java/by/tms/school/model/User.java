package by.tms.school.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private int age;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses;
    private Status userStatus;

    public enum Status{
        ONLINE, OFFLINE
    }

}
