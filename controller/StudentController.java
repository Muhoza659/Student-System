
package org.example.studentsystem.controller;

import jakarta.validation.Valid;
import org.example.studentsystem.DTO.StudentsRequestDTO;
import org.example.studentsystem.DTO.StudentsResponseDTO;
import org.example.studentsystem.service.AuthService;
import org.example.studentsystem.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final SchoolService service;
    private final AuthService authService;

    public StudentController(SchoolService service,
                             AuthService authService) {
        this.service     = service;
        this.authService = authService;
    }

    @GetMapping("/me")
    public StudentsResponseDTO getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        return service.getStudentByEmail(userDetails.getUsername());
    }

    @PostMapping
    public StudentsResponseDTO createStudent(
            @Valid @RequestBody StudentsRequestDTO dto) {
        return authService.register(dto);
    }

    @GetMapping
    public List<StudentsResponseDTO> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/email/{email}")
    public StudentsResponseDTO getStudentByEmail(
            @PathVariable String email) {
        return service.getStudentByEmail(email);
    }

    @PutMapping("/email/{email}")
    public StudentsResponseDTO updateStudent(
            @PathVariable String email,
            @Valid @RequestBody StudentsRequestDTO dto) {
        return service.updateStudentByEmail(email, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}