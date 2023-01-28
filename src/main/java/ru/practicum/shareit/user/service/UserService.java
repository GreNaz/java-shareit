package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> get();

    UserDto get(long id);

    UserDto create(User user);

    UserDto update(long id, User user);

    void delete(long id);
}
