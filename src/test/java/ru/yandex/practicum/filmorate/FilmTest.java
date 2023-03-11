package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmTest {
    private static final String blankName = "";
    private static final String notBlankName = "Lord of the rings";
    private static final String anotherNotBlankName = "Hobbit";
    private static final String rightDescription = "The best movie in the history";
    private static final String wrongDescription = "the bad movie the bad movie the bad movie the bad movie the bad" +
            " movie the bad movie the bad movie the bad movie the bad movie the bad movie the bad movie the bad movie" +
            " the bad movie the bad movie the bad movie the bad movie the bad movie the bad movie the bad movie";
    private static final String anotherRightDescription = "The good film";
    private static final LocalDate rightReleaseDate = LocalDate.of(2001, 12, 19);
    private static final LocalDate anotherRightReleaseDate = LocalDate.of(2003, 1, 23);
    private static final LocalDate wrongReleaseDate = LocalDate.of(1895, 12, 27);
    private static final Duration duration = Duration.ofMinutes(120);
    private FilmController filmController;

    @BeforeEach
    void createFilmController() {
        filmController = new FilmController();
    }

    @Test
    void wouldBlankNameForPost() {
        Film film = filmController.addFilm(blankName, rightDescription, rightReleaseDate, duration);
        assertNull(film);
    }

    @Test
    void wouldNotBlankNameForPost() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        assertEquals(notBlankName, film.getName());
    }

    @Test
    void wouldNotBlankNameForPut() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        film.setName(anotherNotBlankName);
        film = filmController.updateFilm(film);
        assertEquals(anotherNotBlankName, film.getName());
    }

    @Test
    void wouldBlankNameForPut() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        film.setName(blankName);
        assertNull(filmController.updateFilm(film));
    }

    @Test
    void wouldWrongDescriptionForPost() {
        Film film = filmController.addFilm(notBlankName, wrongDescription, rightReleaseDate, duration);
        assertNull(film);
    }

    @Test
    void wouldRightDescriptionForPost() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        assertEquals(rightDescription, film.getDescription());
    }

    @Test
    void wouldRightDescriptionForPut() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        film.setDescription(anotherRightDescription);
        film = filmController.updateFilm(film);
        assertEquals(anotherRightDescription, film.getDescription());
    }

    @Test
    void wouldWrongDescriptionForPut() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        film.setDescription(wrongDescription);
        assertNull(filmController.updateFilm(film));
    }

    @Test
    void wouldRightReleaseDateForPost() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        assertEquals(rightReleaseDate, film.getReleaseDate());
    }

    @Test
    void wouldWrongReleaseDateForPost() {
        Film film = filmController.addFilm(notBlankName, rightDescription, wrongReleaseDate, duration);
        assertNull(film);
    }

    @Test
    void wouldRightReleaseDateForPut() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        film.setReleaseDate(anotherRightReleaseDate);
        film = filmController.updateFilm(film);
        assertEquals(anotherRightReleaseDate, film.getReleaseDate());
    }

    @Test
    void wouldWrongReleaseDateForPut() {
        Film film = filmController.addFilm(notBlankName, rightDescription, rightReleaseDate, duration);
        film.setReleaseDate(wrongReleaseDate);
        assertNull(filmController.updateFilm(film));
    }
}
