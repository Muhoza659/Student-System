package org.example.studentsystem.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarksRequestDTO {



        @NotNull(message = "Student ID is required")
        private Long studentId;

        @NotNull(message = "Course ID is required")
        private Long courseId;

        @NotNull(message = "Marks value is required")
        @Min(value = 0, message = "Marks cannot be less than 0")
        @Max(value = 100, message = "Marks cannot exceed 100")
        private Double marks;

}