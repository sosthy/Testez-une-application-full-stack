package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        SessionDto dto = new SessionDto();
        dto.setDescription("Java Course");
        dto.setTeacher_id(1L);
        dto.setUsers(Arrays.asList(10L, 20L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");

        User user1 = new User();
        user1.setId(10L);
        user1.setEmail("alice@example.com");

        User user2 = new User();
        user2.setId(20L);
        user2.setEmail("bob@example.com");

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(10L)).thenReturn(user1);
        when(userService.findById(20L)).thenReturn(user2);

        Session session = sessionMapper.toEntity(dto);

        assertNotNull(session);
        assertEquals("Java Course", session.getDescription());
        assertEquals(1L, session.getTeacher().getId());
        assertEquals(2, session.getUsers().size());
    }

    @Test
    void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstName("Jane");

        User user = new User();
        user.setId(30L);
        user.setEmail("eve@example.com");

        Session session = new Session();
        session.setDescription("Python Course");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user));

        SessionDto dto = sessionMapper.toDto(session);

        assertNotNull(dto);
        assertEquals("Python Course", dto.getDescription());
        assertEquals(2L, dto.getTeacher_id());
        assertEquals(1, dto.getUsers().size());
    }

    @Test
    void testToEntityList() {
        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setId(1L);
        sessionDto1.setName("John Doe");

        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setId(2L);
        sessionDto2.setName("Jane Smith");

        List<SessionDto> dtoList = Arrays.asList(sessionDto1, sessionDto2);

        List<Session> entityList = sessionMapper.toEntity(dtoList);

        assertEquals(2, entityList.size());
        assertEquals("John Doe", entityList.get(0).getName());
    }

    @Test
    void testToDtoList() {
        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("John Doe");

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Jane Smith");

        List<Session> entityList = Arrays.asList(session1, session2);

        List<SessionDto> dtoList = sessionMapper.toDto(entityList);

        assertEquals(2, dtoList.size());
        assertEquals("Jane Smith", dtoList.get(1).getName());
    }
}

