package org.example.studentsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.studentsystem.entity.Marks;
import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByStudentsId(Long studentId);
}