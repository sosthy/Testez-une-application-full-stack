package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void testCreateSession() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session created = sessionService.create(session);

        assertNotNull(created);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testFindAllSessions() {
        List<Session> sessions = Arrays.asList(new Session(), new Session());
        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> result = sessionService.findAll();

        assertEquals(2, result.size());
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Session session = new Session();
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Session result = sessionService.getById(1L);

        assertNotNull(result);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session updated = sessionService.update(1L, session);

        assertNotNull(updated);
        assertEquals(1L, updated.getId());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testParticipate() {
        Session session = new Session();
        User user = new User();
        session.setUsers(new ArrayList<>());
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        sessionService.participate(1L, 1L);

        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testParticipateThrowsNotFoundExceptionBySession() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    @Test
    void testParticipateThrowsBadRequestExceptionByAlreadyParticipate() {
        Session session = new Session();
        User user = new User();
        user.setId(1L);
        session.setUsers(Collections.singletonList(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L));
    }

    @Test
    void testNoLongerParticipate() {
        User user = new User();
        user.setId(1L);
        Session session = new Session();
        session.setUsers(Arrays.asList(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        sessionService.noLongerParticipate(1L, 1L);

        assertFalse(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testNoLongerParticipateThrowsNotFoundException() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }

    @Test
    void testNoLongerParticipateThrowsBadRequestException() {
        Session session = new Session();
        User user = new User();
        user.setId(2L);
        session.setUsers(Collections.singletonList(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }

    @Test
    void testDelete() {
        doNothing().when(sessionRepository).deleteById(1L);

        sessionService.delete(1L);

        verify(sessionRepository, times(1)).deleteById(1L);
    }
}
