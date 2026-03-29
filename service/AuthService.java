package org.example.studentsystem.service;

import org.example.studentsystem.DTO.AuthResponseDTO;
import org.example.studentsystem.DTO.LoginRequestDTO;
import org.example.studentsystem.DTO.StudentsRequestDTO;
import org.example.studentsystem.DTO.StudentsResponseDTO;
import org.example.studentsystem.Exceptions.DuplicateResourceException;
import org.example.studentsystem.Mapper.StudentsMapper;
import org.example.studentsystem.entity.Role;
import org.example.studentsystem.entity.Students;
import org.example.studentsystem.repository.StudentsRepository;
import org.example.studentsystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentsRepository studentRepo;
    private final StudentsMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(StudentsRepository studentRepo,
                       StudentsMapper studentMapper,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.studentRepo = studentRepo;
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public StudentsResponseDTO register(StudentsRequestDTO dto) {

        if (studentRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException(
                    "Student already exists with email: " + dto.getEmail());
        }

        Students student = studentMapper.toEntity(dto);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));

        student.setRole(dto.getRole() != null ? dto.getRole() : Role.STUDENT);

        return studentMapper.toResponseDTO(studentRepo.save(student));
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(), dto.getPassword()));

        Students student = studentRepo.findByEmail(dto.getEmail())
                .orElseThrow();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles(student.getRole().name())
                .build();
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token, student.getEmail(),
                student.getFirstName(), student.getRole());
    }
}
