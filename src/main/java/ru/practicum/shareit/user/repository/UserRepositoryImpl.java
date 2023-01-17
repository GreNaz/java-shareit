package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.error.BadRequestException;
import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.practicum.shareit.user.UserMapper.toUser;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final HashMap<Long, User> users = new HashMap<>();
    private long ID = 0L;

    @Override
    public List<User> get() {
        return List.copyOf(users.values());
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (Objects.isNull(userDto.getEmail())) {
            throw new BadRequestException("Email not specified");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new BadRequestException("Incorrect email address");
        }
        if (users.containsValue(userDto)) {
            throw new ConflictException("User with email = " + userDto.getEmail() + " already exist");
        }
        userDto.setId(++ID);
        users.put(userDto.getId(), toUser(userDto));
        return userDto;
    }

    @Override
    public User update(long id, User user) {
        user.setId(id);
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
        return user;
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }
}
