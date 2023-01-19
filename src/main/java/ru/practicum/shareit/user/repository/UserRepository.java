package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserDto> get();

    Optional<UserDto> get(long id);

    UserDto create(UserDto userDto);

    UserDto update(long id, UserDto user);

    void delete(long id);
}
