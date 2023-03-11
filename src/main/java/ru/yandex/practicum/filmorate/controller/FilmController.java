package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
public class FilmController {

    private Integer id = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    private static final LocalDate birthdayOfFilms = LocalDate.of(1895, 12,28);
    private static final Integer maxSizeOfDescription = 200;

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        validate(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        film.setId(++id);
        films.put(film.getId(), film);
        log.info(film + "создан");
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        validate(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info(film + "изменен");
        } else {
            throw new ValidationException();
        }
        return film;
    }

    @GetMapping("/films")
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    private void validate(String name, String description, LocalDate releaseDate, Integer duration) throws ValidationException {
        if (name.isBlank()) {
            throw new ValidationException();
        }
        if (description.length() > maxSizeOfDescription) {
            throw new ValidationException();
        }
        if (releaseDate.isBefore(birthdayOfFilms)) {
            throw new ValidationException();
        }
        if (duration <= 0) {
            throw new ValidationException();
        }
    }
}
