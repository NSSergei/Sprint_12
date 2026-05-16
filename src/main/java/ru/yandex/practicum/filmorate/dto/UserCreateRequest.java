package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Data
public class UserCreateRequest extends User {
        Long id;
        String login;
        String name;
        String email;
        LocalDate birthday;
}
