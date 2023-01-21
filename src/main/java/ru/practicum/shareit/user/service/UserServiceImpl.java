package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.BadRequestException;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> get() {
        return userRepository.get();
    }

    @Override
    public UserDto get(long id) {
        return UserMapper.toUserDto(userRepository.get(id).orElseThrow(()
                -> new BadRequestException("User with id = " + id + " not found")));
    }

    @Override
    public UserDto create(UserDto userDto) {
//        if (Objects.isNull(userDto.getEmail())) {
//            throw new BadRequestException("Email not specified");
//        }
//        if (!userDto.getEmail().contains("@")) {
//            throw new BadRequestException("Incorrect email address");
//        }
        return userRepository.create(userDto);
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        return userRepository.update(id, userDto);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }
}
