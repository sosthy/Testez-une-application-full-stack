package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SessionMapper sessionMapper;
    @MockBean
    private SessionService sessionService;

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_Success() throws Exception {
        Session session = new Session();
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Session Yoga");

        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Session Yoga"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_NotFound() throws Exception {
        when(sessionService.getById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_BadRequest() throws Exception {
        mockMvc.perform(get("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindAll_Success() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga");

        when(sessionMapper.toDto(anyList())).thenReturn(Collections.singletonList(sessionDto));
        when(sessionService.findAll()).thenReturn(Collections.singletonList(session));

        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Yoga"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testCreate_Success() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga");

        String json = "{\"name\": \"Yoga\", \"teacher_id\": 1, \"date\": \"2030-01-01\", \"description\": \"Cours de Yoga\"}";

        when(this.sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(this.sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(this.sessionService.create(any(Session.class))).thenReturn(session);

        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Yoga"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testUpdate_Success() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga");

        String json = "{\"id\": 1, \"name\": \"Yoga\", \"teacher_id\": 1, \"date\": \"2030-01-01\", \"description\": \"Cours de Yoga\"}";

        when(this.sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(this.sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(this.sessionService.update(anyLong(), any(Session.class))).thenReturn(session);

        mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Yoga"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testUpdate_ThrowNumberFormatException() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Yoga");

        String json = "{\"id\": 1, \"name\": \"Yoga\", \"teacher_id\": 1, \"date\": \"2030-01-01\", \"description\": \"Cours de Yoga\"}";

        when(this.sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(this.sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(this.sessionService.update(anyLong(), any(Session.class))).thenReturn(session);

        mockMvc.perform(put("/api/session/invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testSave_Success() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        when(sessionService.getById(anyLong())).thenReturn(session);
        doNothing().when(sessionService).delete(anyLong());

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testSave_NotFound() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        when(sessionService.getById(anyLong())).thenReturn(null);
        doNothing().when(sessionService).delete(anyLong());

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testSave_BadResquest() throws Exception {
        Session session = new Session();
        session.setName("Yoga");

        when(sessionService.getById(anyLong())).thenReturn(null);
        doNothing().when(sessionService).delete(anyLong());

        mockMvc.perform(delete("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testParticipate_Success() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/100"))
                .andExpect(status().isOk());

        verify(sessionService, times(1)).participate(1L, 100L);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testParticipate_BadRequest() throws Exception {
        mockMvc.perform(post("/api/session/abc/participate/xyz"))
                .andExpect(status().isBadRequest());

        verify(sessionService, never()).participate(anyLong(), anyLong());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testNoLongerParticipateSuccess() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/100"))
                .andExpect(status().isOk());

        verify(sessionService, times(1)).noLongerParticipate(1L, 100L);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testNoLongerParticipateBadRequest() throws Exception {
        mockMvc.perform(delete("/api/session/xyz/participate/abc"))
                .andExpect(status().isBadRequest());

        verify(sessionService, never()).noLongerParticipate(anyLong(), anyLong());
    }

}
