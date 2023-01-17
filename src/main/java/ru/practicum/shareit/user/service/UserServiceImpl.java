package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> get() {
    }

    @Override
    public User get(long id) {
    }

    @Override
    public UserDto create(UserDto userDto) {
    }

    @Override
    public User update(long id, User user) {
    }

    @Override
    public void delete(long id) {
    }
}
