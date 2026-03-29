package org.example.studentsystem.Mapper;

import org.example.studentsystem.DTO.StudentsRequestDTO;
import org.example.studentsystem.DTO.StudentsResponseDTO;
import org.example.studentsystem.entity.Students;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marks", ignore = true)
    Students toEntity(StudentsRequestDTO dto);

    StudentsResponseDTO toResponseDTO(Students student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marks", ignore = true)
    void updateEntityFromDTO(StudentsRequestDTO dto, @MappingTarget Students student);
}