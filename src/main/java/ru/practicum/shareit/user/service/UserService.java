package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> get();

    UserDto get(long id);

    UserDto create(UserDto userDto);

    UserDto update(long id, UserDto user);

    void delete(long id);
}
