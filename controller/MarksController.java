package org.example.studentsystem.controller;



import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.example.studentsystem.service.SchoolService;
import org.example.studentsystem.DTO.MarksRequestDTO;
import org.example.studentsystem.DTO.MarksResponseDTO;

@RestController
@RequestMapping("/marks")
public class MarksController {

    private final SchoolService service;

    public MarksController(SchoolService service){
        this.service = service;
    }


    @PostMapping
    public MarksResponseDTO createMarks(@Valid @RequestBody MarksRequestDTO dto){
        return service.createMarks(dto);
    }

    @GetMapping
    public List<MarksResponseDTO> getAllMarks(){
        return service.getAllMarks();
    }


    @GetMapping("/student/{studentId}")
    public List<MarksResponseDTO> getMarksByStudentId(@PathVariable Long studentId){
        return service.getMarksByStudentId(studentId);
    }

    @PutMapping("/{id}")
    public MarksResponseDTO updateMarks(
            @PathVariable Long id,
            @RequestBody MarksRequestDTO dto){
        return service.updateMarks(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteMarks(@PathVariable Long id){
        service.deleteMarks(id);
    }
}