package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {
    private HashMap<Long, User> users;

    @PostMapping
    public User create(
            @Valid
            @RequestBody User user) {
        log.info("Received a request to add a new user");
        return users.put(user.getId(), user);
    }

    @PutMapping
    public User put(
            @Valid
            @RequestBody User user) {
        log.info("Received a request to update a user with id {}", user.getId());
        return users.put(user.getId(), user);
    }

    @GetMapping
    public List<User> get() {
        log.info("Received a request to get all users");
        return List.copyOf(users.values());
    }

    @GetMapping("/{id}")
    public User get(
            @PathVariable Long id) {
        log.info("Received a request to get user with id: {} ", id);
        return users.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {
        log.info("Received a request to remove user with id: {} ", id);
        users.remove(id);
    }
}
