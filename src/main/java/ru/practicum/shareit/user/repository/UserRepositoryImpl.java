package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.BadRequestException;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.model.UserMapper.toUser;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final HashMap<Long, User> users = new HashMap<>();
    private static long userId = 0L;

    @Override
    public List<UserDto> get() {
        return List.copyOf(
                users.values().stream()
                        .map(UserMapper::toUserDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        if (Objects.isNull(user.getEmail())) {
            throw new BadRequestException("Email address can not be null");
        }
        if (!user.getEmail().contains("@")) {
            throw new BadRequestException("Incorrect email address");
        }
        if (users.containsValue(user)) {
            throw new ConflictException("User with email = " + user.getEmail() + " already exist");
        }
        userDto.setId(++userId);
        users.put(userDto.getId(), toUser(userDto));
        return userDto;
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        userDto.setId(id);
        User user = UserMapper.toUser(userDto);
        if (Objects.isNull(user.getName())) {
            if (!user.getEmail().contains("@")) {
                throw new BadRequestException("Incorrect email address");
            }
            if (users.containsValue(user)) {
                throw new ConflictException("User with email = " + user.getEmail() + " already exist");
            }
            users.get(id).setEmail(user.getEmail());
            user.setName(users.get(id).getName());
        }
        if (Objects.isNull(user.getEmail())) {
            users.get(id).setName(user.getName());
            user.setEmail(users.get(id).getEmail());
        }
        users.put(id, user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }
}
