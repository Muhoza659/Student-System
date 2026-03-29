package org.example.studentsystem.Mapper;

import org.example.studentsystem.DTO.MarksRequestDTO;
import org.example.studentsystem.DTO.MarksResponseDTO;
import org.example.studentsystem.entity.Marks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarksMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "courses",  ignore = true)
    Marks toEntity(MarksRequestDTO dto);


    @Mapping(source = "students.id", target = "studentId")
    @Mapping(source = "courses.id",  target = "courseId")
    MarksResponseDTO toResponseDTO(Marks marks);


    @Mapping(target = "students", ignore = true)
    @Mapping(target = "courses",  ignore = true)
    void updateEntityFromDTO(MarksRequestDTO dto, @MappingTarget Marks marks);
}