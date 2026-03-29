package org.example.studentsystem.security;

import org.example.studentsystem.entity.Role;
import org.example.studentsystem.entity.Students;
import org.example.studentsystem.repository.StudentsRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentsRepository studentRepo;

    public UserDetailsServiceImpl(StudentsRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Students student = studentRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Student not found with email: " + email));
        Role role = student.getRole() != null
                ? student.getRole()
                : Role.STUDENT;

        return User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles(role.name())
                .build();
    }
}
