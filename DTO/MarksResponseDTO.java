package org.example.studentsystem.DTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MarksResponseDTO {

    private Long id;
    private Long studentId;
    private Long courseId;
    private Double marks;
}

