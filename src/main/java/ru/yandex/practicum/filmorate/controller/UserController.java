package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private Integer id = 0;
    private final HashMap<Integer, User> users = new HashMap<>();

    @PostMapping("/users")
    public User createUser(@RequestBody User user) throws ValidationException {
        validate(user.getEmail(), user.getLogin(), user.getBirthday());
        String name = validateName(user.getName(), user.getLogin());
        user.setName(name);
        user.setId(++id);
        users.put(user.getId(), user);
        log.info(user + "создан");
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws ValidationException {
        validate(user.getEmail(), user.getLogin(), user.getBirthday());
        String name = validateName(user.getName(), user.getLogin());
        user.setName(name);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException();
        }
        log.info(user + "изменен");
        return user;
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return users.values();
    }

    private void validate(String email, String login, LocalDate birthday) throws ValidationException {
        if (login.contains(" ") || login.isBlank()) {
            throw new ValidationException();
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException();
        }
        if (email.isBlank() || !email.contains("@")) {
            throw new ValidationException();
        }
    }

    private String validateName(String name, String login) {
        if (name == null || name.isBlank()) {
            name = login;
        }
        return name;
    }
}
