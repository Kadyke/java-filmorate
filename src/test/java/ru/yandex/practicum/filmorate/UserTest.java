package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    private static final String rightEmail = "practicum@yandex.ru";
    private static final String anotherRightEmail = "student@yandex.ru";
    private static final String wrongEmail = "23124.1sdgs.skum";
    private static final String blankEmail = "";
    private static final String rightLogin = "practicum@yandex.ru";
    private static final String anotherRightLogin = "practicum";
    private static final String wrongLogin = "23124. 1sdgs@. skum";
    private static final String blankLogin = "";
    private static final String blankName = "";
    private static final String notBlankName = "practicum";
    private static final String anotherNotBlankName = "student";
    private static final LocalDate rightBirthday = LocalDate.now().minusMonths(60);
    private static final LocalDate anotherRightBirthday = LocalDate.now().minusMonths(100);
    private static final LocalDate wrongBirthday = LocalDate.now().plusMonths(60);
    private UserController userController;

    @BeforeEach
    void createUserController() {
        userController = new UserController();
    }

    @Test
    void wouldWrongEmailForPost() {
        User user = userController.createUser(wrongEmail, rightLogin, notBlankName, rightBirthday);
        assertNull(user);
        user = userController.createUser(blankEmail, rightLogin, notBlankName, rightBirthday);
        assertNull(user);
    }

    @Test
    void wouldRightEmailForPost() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        assertEquals(user.getEmail(), rightEmail);
    }

    @Test
    void wouldWrongEmailForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setEmail(wrongEmail);
        assertNull(userController.updateUser(user));
        user.setEmail(blankEmail);
        assertNull(userController.updateUser(user));
    }

    @Test
    void wouldRightEmailForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setEmail(anotherRightEmail);
        user = userController.updateUser(user);
        assertEquals(user.getEmail(), anotherRightEmail);
    }

    @Test
    void wouldWrongLoginForPost() {
        User user = userController.createUser(rightEmail, wrongLogin, notBlankName, rightBirthday);
        assertNull(user);
        user = userController.createUser(rightEmail, blankLogin, notBlankName, rightBirthday);
        assertNull(user);
    }

    @Test
    void wouldRightLoginForPost() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        assertEquals(user.getLogin(), rightEmail);
    }

    @Test
    void wouldWrongLoginForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setLogin(wrongLogin);
        assertNull(userController.updateUser(user));
        user.setLogin(blankLogin);
        assertNull(userController.updateUser(user));
    }

    @Test
    void wouldRightLoginForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setLogin(anotherRightLogin);
        user = userController.updateUser(user);
        assertEquals(user.getLogin(), anotherRightLogin);
    }

    @Test
    void wouldBlankNameForPost() {
        User user = userController.createUser(rightEmail, rightLogin, blankName, rightBirthday);
        assertEquals(user.getName(), rightLogin);
    }

    @Test
    void wouldNotBlankNameForPost() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        assertEquals(user.getName(), notBlankName);
    }

    @Test
    void wouldBlankNameForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setName(" ");
        user = userController.updateUser(user);
        assertEquals(user.getName(), rightLogin);
    }

    @Test
    void wouldNotBlankNameForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setName(anotherNotBlankName);
        user = userController.updateUser(user);
        assertEquals(user.getName(), anotherNotBlankName);
    }

    @Test
    void wouldWrongBirthdayForPost() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, wrongBirthday);
        assertNull(user);
    }

    @Test
    void wouldRightBirthdayForPost() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        assertEquals(user.getBirthday(), rightBirthday);
    }

    @Test
    void wouldWrongBirthdayForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setBirthday(wrongBirthday);
        assertNull(userController.updateUser(user));
    }

    @Test
    void wouldRightBirthdayForPut() {
        User user = userController.createUser(rightEmail, rightLogin, notBlankName, rightBirthday);
        user.setBirthday(anotherRightBirthday);
        user = userController.updateUser(user);
        assertEquals(user.getBirthday(), anotherRightBirthday);
    }
}
