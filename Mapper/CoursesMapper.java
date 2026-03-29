package org.example.studentsystem.Mapper;

import org.example.studentsystem.DTO.CoursesRequestDTO;
import org.example.studentsystem.DTO.CoursesResponseDTO;
import org.example.studentsystem.entity.Courses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoursesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marks", ignore = true)
    Courses toEntity(CoursesRequestDTO dto);

    CoursesResponseDTO toResponseDTO(Courses course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marks", ignore = true)
    void updateEntityFromDTO(CoursesRequestDTO dto, @MappingTarget Courses course);
}