package org.example.studentsystem.controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.example.studentsystem.service.SchoolService;
import org.example.studentsystem.DTO.CoursesRequestDTO;
import org.example.studentsystem.DTO.CoursesResponseDTO;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final SchoolService service;

    public CourseController(SchoolService service){
        this.service = service;
    }

    @PostMapping
    public CoursesResponseDTO createCourse(@Valid @RequestBody CoursesRequestDTO dto){
        return service.createCourse(dto);
    }


    @GetMapping
    public List<CoursesResponseDTO> getAllCourses(){
        return service.getAllCourses();
    }

    @GetMapping("/{id}")
    public CoursesResponseDTO getCourse(@PathVariable Long id){
        return service.getCourse(id);
    }


    @PutMapping("/{id}")
    public CoursesResponseDTO updateCourse(
            @PathVariable Long id,
            @RequestBody CoursesRequestDTO dto){
        return service.updateCourse(id, dto);
    }


    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        service.deleteCourse(id);
    }
}