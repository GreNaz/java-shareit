package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.ConflictException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private long userId = 0L;

    @Override
    public List<User> get() {
        return List.copyOf(users.values());
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User create(User user) {
        if (users.containsValue(user)) {
            throw new ConflictException("User with email = " + user.getEmail() + " already exist");
        }
        user.setId(++userId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(long id, User user) {
        User userInMem = users.get(id);
        if (!Objects.isNull(user.getEmail()) && !user.getEmail().isBlank()) {
            if (users.containsValue(user)) {
                throw new ConflictException("User with email = " + user.getEmail() + " already exist");
            }
            userInMem.setEmail(user.getEmail());
        }
        if (!Objects.isNull(user.getName()) && !user.getName().isBlank()) {
            userInMem.setName(user.getName());
        }
        return userInMem;
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }
}
