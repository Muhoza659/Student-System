package org.example.studentsystem.controller;

import jakarta.validation.Valid;
import org.example.studentsystem.DTO.AuthResponseDTO;
import org.example.studentsystem.DTO.LoginRequestDTO;
import org.example.studentsystem.DTO.StudentsRequestDTO;
import org.example.studentsystem.DTO.StudentsResponseDTO;
import org.example.studentsystem.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public StudentsResponseDTO register(@Valid @RequestBody StudentsRequestDTO dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }
}