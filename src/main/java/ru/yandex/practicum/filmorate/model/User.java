package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

}
