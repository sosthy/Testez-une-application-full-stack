package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void testFindAllTeachers() {
        List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());
        when(teacherRepository.findAll()).thenReturn(teachers);

        List<Teacher> result = teacherService.findAll();

        assertEquals(2, result.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(new Teacher()));

        Teacher result = teacherService.findById(1L);

        assertNotNull(result);
        verify(teacherRepository, times(1)).findById(1L);
    }
}
