package org.example.studentsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import org.example.studentsystem.entity.*;
import org.example.studentsystem.repository.*;
import org.example.studentsystem.DTO.*;
import org.example.studentsystem.Mapper.*;
import org.example.studentsystem.Exceptions.DuplicateResourceException;
import org.example.studentsystem.Exceptions.ResourceNotFoundException;

@Service
public class SchoolService {

    private final StudentsRepository studentRepo;
    private final CoursesRepository courseRepo;
    private final MarksRepository marksRepo;
    private final PasswordEncoder passwordEncoder;

    private final StudentsMapper studentMapper;
    private final CoursesMapper courseMapper;
    private final MarksMapper marksMapper;

    public SchoolService(StudentsRepository studentRepo,
                         CoursesRepository courseRepo,
                         MarksRepository marksRepo,
                         StudentsMapper studentMapper,
                         CoursesMapper courseMapper,
                         MarksMapper marksMapper,
                         PasswordEncoder passwordEncoder){

        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.marksRepo = marksRepo;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.marksMapper = marksMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // for students

//    public StudentsResponseDTO createStudent(StudentsRequestDTO dto) {
//
//        if (studentRepo.findByEmail(dto.getEmail()).isPresent()) {
//            throw new DuplicateResourceException("Student already exists with email: " + dto.getEmail());
//        }
//
//        Students student = studentMapper.toEntity(dto);
//        return studentMapper.toResponseDTO(studentRepo.save(student));
//    }

    public List<StudentsResponseDTO> getAllStudents() {
        return studentRepo.findAll()
                .stream()
                .map(studentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public StudentsResponseDTO getStudentByEmail(String email) {

        Students student = studentRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));

        return studentMapper.toResponseDTO(student);
    }

    public StudentsResponseDTO updateStudentByEmail(String email, StudentsRequestDTO dto) {

        Students student = studentRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
        if(dto.getPassword() != null){
            student.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        studentMapper.updateEntityFromDTO(dto, student);

        return studentMapper.toResponseDTO(studentRepo.save(student));
    }

    public void deleteStudent(Long id) {

        Students student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        studentRepo.delete(student);
    }

    // for courses

    public CoursesResponseDTO createCourse(CoursesRequestDTO dto) {

        Courses course = courseMapper.toEntity(dto);
        return courseMapper.toResponseDTO(courseRepo.save(course));
    }

    public List<CoursesResponseDTO> getAllCourses() {

        return courseRepo.findAll()
                .stream()
                .map(courseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CoursesResponseDTO getCourse(Long id) {

        Courses course = courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        return courseMapper.toResponseDTO(course);
    }

    public CoursesResponseDTO updateCourse(Long id, CoursesRequestDTO dto) {

        Courses course = courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        courseMapper.updateEntityFromDTO(dto, course);

        return courseMapper.toResponseDTO(courseRepo.save(course));
    }

    public void deleteCourse(Long id) {

        Courses course = courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        courseRepo.delete(course);
    }

    // for marks
    public MarksResponseDTO createMarks(MarksRequestDTO dto) {
        Students student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + dto.getStudentId()));

        Courses course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + dto.getCourseId()));

        Marks marks = marksMapper.toEntity(dto);
        marks.setStudents(student);
        marks.setCourses(course);

        return marksMapper.toResponseDTO(marksRepo.save(marks));
    }

    public List<MarksResponseDTO> getAllMarks() {
        return marksRepo.findAll()
                .stream()
                .map(marksMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MarksResponseDTO> getMarksByStudentId(Long studentId) {
        studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        return marksRepo.findByStudentsId(studentId)
                .stream()
                .map(marksMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MarksResponseDTO updateMarks(Long id, MarksRequestDTO dto) {
        Marks marks = marksRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Marks not found with id: " + id));

        Students student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + dto.getStudentId()));

        Courses course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + dto.getCourseId()));

        marksMapper.updateEntityFromDTO(dto, marks);
        marks.setStudents(student);
        marks.setCourses(course);

        return marksMapper.toResponseDTO(marksRepo.save(marks));
    }

    public void deleteMarks(Long id) {
        Marks marks = marksRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Marks not found with id: " + id));
        marksRepo.delete(marks);
    }
}