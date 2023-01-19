package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.error.BadRequestException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> get() {
    }

    @Override
    public UserDto get(long id) {
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (Objects.isNull(userDto.getEmail())) {
            throw new BadRequestException("Email not specified");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new BadRequestException("Incorrect email address");
        }
        return userRepository.create(userDto);
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
    }

    @Override
    public void delete(long id) {
    }
}
