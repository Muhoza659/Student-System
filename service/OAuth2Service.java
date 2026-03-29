
package org.example.studentsystem.service;

import org.example.studentsystem.DTO.AuthResponseDTO;
import org.example.studentsystem.entity.Role;
import org.example.studentsystem.entity.Students;
import org.example.studentsystem.repository.StudentsRepository;
import org.example.studentsystem.security.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OAuth2Service {

    private final StudentsRepository studentRepo;
    private final JwtService jwtService;

    public OAuth2Service(StudentsRepository studentRepo,
                         JwtService jwtService) {
        this.studentRepo = studentRepo;
        this.jwtService  = jwtService;
    }


    public AuthResponseDTO processOAuth2Login(OAuth2User oauth2User,
                                              String provider) {

        String email     = extractEmail(oauth2User, provider);
        String firstName = extractFirstName(oauth2User, provider);
        String lastName  = extractLastName(oauth2User, provider);


        Students student = findOrCreateStudent(email, firstName,
                lastName, provider);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles(student.getRole().name())
                .build();

        String token = jwtService.generateToken(userDetails);


        return new AuthResponseDTO(token, student.getEmail(),
                student.getFirstName(), student.getRole());
    }


    private Students findOrCreateStudent(String email,
                                         String firstName,
                                         String lastName,
                                         String provider) {
        return studentRepo.findByEmail(email)
                .orElseGet(() -> {
                    Students newStudent = new Students();
                    newStudent.setEmail(email);
                    newStudent.setFirstName(firstName);
                    newStudent.setLastName(lastName);
                    newStudent.setPassword("OAUTH2_" + provider.toUpperCase());
                    newStudent.setDateOfBirth(LocalDate.of(2000, 1, 1));
                    newStudent.setRole(Role.STUDENT);
                    return studentRepo.save(newStudent);
                });
    }


    private String extractEmail(OAuth2User user, String provider) {
        String email = user.getAttribute("email");

        if (email == null && provider.equals("github")) {

            String login = user.getAttribute("login");
            email = login + "@github.com";
        }

        if (email == null) {
            throw new RuntimeException("Email not found from " + provider);
        }

        return email;
    }


    private String extractFirstName(OAuth2User user, String provider) {
        if (provider.equals("google")) {

            String name = user.getAttribute("first_name");
            return name != null ? name : "User";
        }
        if (provider.equals("github")) {

            String name = user.getAttribute("login");
            return name != null ? name : "User";
        }
        return "User";
    }

    private String extractLastName(OAuth2User user, String provider) {
        if (provider.equals("google")) {

            String name = user.getAttribute("last_name");
            return name != null ? name : "";
        }

        return "";
    }
}