package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private Integer id = 0;
    private final HashMap<Integer, User> users = new HashMap<>();

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody String email, String login, String name, LocalDate birthday) {
        User user = new User(++id);
        try {
            validate(email, login, birthday);
            name = validateName(name, login);
            user.setName(name);
            user.setEmail(email);
            user.setLogin(login);
            user.setBirthday(birthday);
            users.put(user.getId(), user);
            log.info(user + "создан");
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            return null;
        }
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody @Valid User user) {
        try {
            validate(user.getEmail(), user.getLogin(), user.getBirthday());
            String name = validateName(user.getName(), user.getLogin());
            user.setName(name);
            users.put(user.getId(), user);
            log.info(user + "изменен");
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            return null;
        }
        return user;
    }

    @GetMapping("/user")
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
        if (name.isBlank()) {
            name = login;
        }
        return name;
    }
}
