package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testAuthenticateUserSuccess() throws Exception {
        // Mock user details
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@example.com", "John", "Doe", true, "password");
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);

        // Mock authentication
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtUtils.generateJwtToken(auth)).thenReturn("mocked-jwt");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User("test@example.com", "Doe", "John", "password", false)));

        // JSON request
        String loginJson = "    {\n" +
                           "        \"email\": \"test@example.com\",\n" +
                           "        \"password\": \"password\"\n" +
                           "    }\n";

        // Execute request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.admin").value(false));
    }
/*
    @Test
    void testAuthenticateUserFailure() throws Exception {
        // Simulate failed authentication
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Bad credentials"));

        // JSON request
        String loginJson = "    {\n" +
                           "        \"email\": \"wrong@example.com\",\n" +
                           "        \"password\": \"wrongpassword\"\n" +
                           "    }\n";

        // Execute request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().is5xxServerError());
    }
*/
    @Test
    void testRegisterUserSuccess() throws Exception {
        // Simulate user not existing
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("securepassword")).thenReturn("hashedpassword");

        // JSON request
        String registerJson = "    {\n" +
                              "        \"email\": \"newuser@example.com\",\n" +
                              "        \"firstName\": \"Alice\",\n" +
                              "        \"lastName\": \"Smith\",\n" +
                              "        \"password\": \"securepassword\"\n" +
                              "    }\n";

        // Execute request
        mockMvc.perform(post("/api/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));

        // Verify that the user is saved
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserEmailAlreadyExists() throws Exception {
        // Simulate existing user
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // JSON request
        String registerJson = "    {\n" +
                              "        \"email\": \"existing@example.com\",\n" +
                              "        \"firstName\": \"Bob\",\n" +
                              "        \"lastName\": \"Brown\",\n" +
                              "        \"password\": \"password123\"\n" +
                              "    }\n";

        // Execute request
        mockMvc.perform(post("/api/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));

        // Verify that save() is never called
        verify(userRepository, never()).save(any(User.class));
    }
}
