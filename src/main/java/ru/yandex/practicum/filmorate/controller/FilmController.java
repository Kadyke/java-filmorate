package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@Slf4j
public class FilmController {

    private Integer id = 0;
    private final HashMap<Integer, Film> films = new HashMap();

    private static final LocalDate birthdayOfFilms = LocalDate.of(1895, 12,28);
    private static final Integer maxSizeOfDescription = 200;

    @PostMapping("/film")
    public Film addFilm(@RequestBody String name, String description, LocalDate releaseDate, Duration duration)  {
        Film film = new Film(++id);
        try {
            validate(name, description, releaseDate);
            film.setName(name);
            film.setDescription(description);
            film.setReleaseDate(releaseDate);
            film.setDuration(duration);
            films.put(film.getId(), film);
            log.info(film + "создан");
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            return null;
        }
        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@RequestBody Film film) {
        try {
            validate(film.getName(), film.getDescription(), film.getReleaseDate());
            films.put(film.getId(), film);
            log.info(film + "изменен");
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            return null;
        }
        return film;
    }

    @GetMapping("/film")
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    private void validate(String name, String description, LocalDate releaseDate) throws ValidationException {
        if (name.isBlank()) {
            throw new ValidationException();
        }
        if (description.length() > maxSizeOfDescription) {
            throw new ValidationException();
        }
        if (releaseDate.isBefore(birthdayOfFilms)) {
            throw new ValidationException();
        }
    }
}
