package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.validation.Create;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {
    private final UserService userService;


    @PostMapping
    public User create(
            @Validated(Create.class)
            @RequestBody User user) {
        log.info("Received a request to add a new user");

    }

    @PatchMapping("/{id}")
    public User put(
            @Valid
            @PathVariable Long id,
            @RequestBody User user) {
        log.info("Received a request to update a user with id {}", id);

    }

    @GetMapping
    public List<User> get() {
        log.info("Received a request to get all users");

    }

    @GetMapping("/{id}")
    public User get(
            @PathVariable Long id) {
        log.info("Received a request to get user with id: {} ", id);

    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {
        log.info("Received a request to remove user with id: {} ", id);

    }
}