package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

class TeacherMapperTest {
    private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    void testToEntity() {
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastName("Doe");

        Teacher teacher = teacherMapper.toEntity(dto);

        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
    }

    @Test
    void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstName("Jane");
        teacher.setLastName("Smith");

        TeacherDto dto = teacherMapper.toDto(teacher);

        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
    }

    @Test
    void testToEntityList() {
        TeacherDto teacherDto1 = new TeacherDto();
        teacherDto1.setId(1L);
        teacherDto1.setFirstName("John");
        teacherDto1.setLastName("Doe");

        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setFirstName("Jane");
        teacherDto2.setLastName("Smith");

        List<TeacherDto> dtoList = Arrays.asList(teacherDto1, teacherDto2);

        List<Teacher> entityList = teacherMapper.toEntity(dtoList);

        assertEquals(2, entityList.size());
        assertEquals("John", entityList.get(0).getFirstName());
    }

    @Test
    void testToDtoList() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Smith");

        List<Teacher> entityList = Arrays.asList(teacher1, teacher2);

        List<TeacherDto> dtoList = teacherMapper.toDto(entityList);

        assertEquals(2, dtoList.size());
        assertEquals("Jane", dtoList.get(1).getFirstName());
    }
}

