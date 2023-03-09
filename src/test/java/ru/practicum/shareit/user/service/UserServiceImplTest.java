package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.resource.UserData.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void getAll_ReturnEmptyList() {
        when(userRepository.findAll())
                .thenReturn(Collections.emptyList());
        assertTrue(userService.get().isEmpty());
    }

    @Test
    void getById_Success() {
        var expectedUser = getUserDto();
        when(userRepository.findById(0L))
                .thenReturn(Optional.of(getUser()));
        var actualUser = userService.get(0L);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getById_UserNotFound() {
        when(userRepository.findById(0L))
                .thenReturn(Optional.empty());
        var ex = assertThrows(NotFoundException.class,
                () -> userService.get(0L));
        assertEquals("User with id = 0 not found", ex.getMessage());
    }

    @Test
    void create_With_Success() {
        var expectedUserDto = getUserDto();
        var user = getUser();
        when(userRepository.save(any())).thenReturn(user);
        var actualUserDto = userService.create(user);
        assertEquals(expectedUserDto, actualUserDto);
        verify(userRepository).save(any());
    }

//    @Test
//    void update_whenUserForUpdateValid_thenUserUpdated() {
//        User oldUser = getTestUser();
//        UserDto userDtoForUpdate = getTestUserDtoForUpdate();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
//        when(userRepository.save(oldUser)).thenReturn(UserMapper.toUser(userDtoForUpdate));
//
//        UserDto actual = userService.update(1L, getTestUserForUpdate());
//
//        verify(userRepository).save(userArgumentCaptor.capture());
//        User saved = userArgumentCaptor.getValue();
//
//        assertEquals(actual.getId(), saved.getId());
//        assertEquals(actual.getName(), saved.getName());
//        assertEquals(actual.getEmail(), saved.getEmail());
//    }

    @Test
    void update_NotFoundException() {
        when(userRepository.findById(0L))
                .thenReturn(Optional.empty());
        var ex = assertThrows(NotFoundException.class,
                () -> userService.update(0L, getUpdateUser()));
        assertEquals("User with id = 0 not found", ex.getMessage());
    }

//    @Test
//    void delete_verifyInvokingMethod() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(getTestUser()));
//        userService.delete(1L);
//        verify(userRepository).delete(any());
//    }
}