package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> get();

    Optional<User> get(long id);

    User create(User user);

    User update(long id, User user);

    void delete(long id);
}
