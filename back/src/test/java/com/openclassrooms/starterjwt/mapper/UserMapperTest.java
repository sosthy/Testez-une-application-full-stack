package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.mapper.UserMapper;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToEntity() {
        UserDto dto = new UserDto();
        dto.setId(10L);
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("password");
        dto.setEmail("user@example.com");

        User user = userMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals(10L, user.getId());
        assertEquals("user@example.com", user.getEmail());
    }

    @Test
    void testToDto() {
        User user = new User();
        user.setId(20L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setEmail("test@domain.com");

        UserDto dto = userMapper.toDto(user);

        assertNotNull(dto);
        assertEquals(20L, dto.getId());
        assertEquals("test@domain.com", dto.getEmail());
    }

    @Test
    void testToEntityList() {
        UserDto userDto1 = new UserDto();
        userDto1.setId(30L);
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setPassword("password");
        userDto1.setEmail("alice@example.com");

        UserDto userDto2 = new UserDto();
        userDto2.setId(40L);
        userDto2.setFirstName("John");
        userDto2.setLastName("Doe");
        userDto2.setPassword("password");
        userDto2.setEmail("bob@example.com");

        List<UserDto> dtoList = Arrays.asList(userDto1, userDto2);

        List<User> entityList = userMapper.toEntity(dtoList);

        assertEquals(2, entityList.size());
        assertEquals("bob@example.com", entityList.get(1).getEmail());
    }

    @Test
    void testToDtoList() {
        User user1 = new User();
        user1.setId(50L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("password");
        user1.setEmail("charlie@example.com");

        User user2 = new User();
        user2.setId(60L);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setPassword("password");
        user2.setEmail("dave@example.com");

        List<User> entityList = Arrays.asList(user1, user2);

        List<UserDto> dtoList = userMapper.toDto(entityList);

        assertEquals(2, dtoList.size());
        assertEquals("charlie@example.com", dtoList.get(0).getEmail());
    }
}

