package org.example.studentsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    @Max(100)
    private Double marks;

    @ManyToOne

    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Students students;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Courses courses;
}





