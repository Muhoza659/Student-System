package org.example.studentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.studentsystem.entity.Students;

import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Students, Long> {
    Optional<Students> findByEmail(String email);
}