package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_Success() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_NotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testFindById_BadRequest() throws Exception {
        mockMvc.perform(get("/api/user/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testDelete_Success() throws Exception {
        when(userService.findById(1L)).thenReturn(user);
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "other@example.com")
    void testDelete_Unauthorized() throws Exception {
        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testDelete_NotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testDelete_BadRequest() throws Exception {
        mockMvc.perform(delete("/api/user/invalid"))
                .andExpect(status().isBadRequest());
    }
}

