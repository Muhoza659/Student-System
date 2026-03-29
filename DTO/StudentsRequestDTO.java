package org.example.studentsystem.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.example.studentsystem.entity.Role;

import java.time.LocalDate;

@Getter
@Setter
public class StudentsRequestDTO {

        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        private String email;

        @Column(nullable = true)
        private String password;

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        private LocalDate dateOfBirth;

        private Role role = Role.STUDENT;
}