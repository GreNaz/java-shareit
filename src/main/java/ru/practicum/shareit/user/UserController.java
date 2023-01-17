package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.BadRequestException;
import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {
    private static Long ID = 0L;
    private final HashMap<Long, User> users = new HashMap<>();

    @PostMapping
    public User create(
            @Validated(Create.class)
            @RequestBody User user) {
        log.info("Received a request to add a new user");

        if (Objects.isNull(user.getEmail())) {
            throw new BadRequestException("Email not specified");
        }
        if (!user.getEmail().contains("@")) {
            throw new BadRequestException("Incorrect email address");
        }
        if (users.containsValue(user)) {
            throw new ConflictException("User with email = " + user.getEmail() + " already exist");
        }

        user.setId(++ID);
        users.put(user.getId(), user);
        return user;
    }

    @PatchMapping("/{id}")
    public User put(
            @Valid
            @PathVariable Long id,
            @RequestBody User user) {
        log.info("Received a request to update a user with id {}", id);
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