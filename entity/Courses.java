package org.example.studentsystem.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Course name required")
    private String courseName;

    @NotBlank(message="Course description  required")
    private String courseDescription;

    @OneToMany(mappedBy="courses")
    @JsonManagedReference
    private List<Marks> marks;

}
