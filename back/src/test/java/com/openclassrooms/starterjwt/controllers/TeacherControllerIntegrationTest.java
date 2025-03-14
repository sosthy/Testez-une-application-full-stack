package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeacherMapper teacherMapper;
    @MockBean
    private TeacherService teacherService;

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_Success() throws Exception {
        Teacher teacher = new Teacher();
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("John");

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_NotFound() throws Exception {
        when(teacherService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/teacher/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_BadRequest() throws Exception {
        mockMvc.perform(get("/api/teacher/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindAll_Success() throws Exception {
        Teacher teacher = new Teacher();
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("John");

        when(teacherService.findAll()).thenReturn(Collections.singletonList(teacher));
        when(teacherMapper.toDto(Collections.singletonList(teacher))).thenReturn(Collections.singletonList(teacherDto));

        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }
}
