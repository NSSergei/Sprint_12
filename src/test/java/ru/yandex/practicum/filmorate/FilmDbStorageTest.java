package ru.yandex.practicum.filmorate.dao.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import ru.yandex.practicum.filmorate.dao.user.UserDbStorage;
import ru.yandex.practicum.filmorate.dto.UserCreateRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.enums.Genre;
import ru.yandex.practicum.filmorate.model.enums.MpaRating;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({FilmDbStorage.class, UserDbStorage.class})
@Transactional
@Rollback
class FilmDbStorageTest {

    @Autowired
    private FilmDbStorage storage;

    @Autowired
    private UserDbStorage userStorage;

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("Film A");
        film.setDescription("Test film");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        film.setMpa(MpaRating.G);
        film.setGenres(Set.of(Genre.COMEDY, Genre.DRAMA));
    }

    // ===== helper =====
    private User createUser() {
        UserCreateRequest req = new UserCreateRequest();
        req.setEmail("user" + System.nanoTime() + "@mail.com");
        req.setLogin("user");
        req.setName("User");
        req.setBirthday(LocalDate.of(2000, 1, 1));

        return userStorage.addUser(req);
    }

    @Test
    void shouldAddFilm() {
        Film saved = storage.addFilm(film);

        assertNotNull(saved.getId());
        assertEquals("Film A", saved.getName());
    }

    @Test
    void shouldGetFilmById() {
        Film saved = storage.addFilm(film);

        Optional<Film> found = storage.getFilmById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void shouldUpdateFilm() {
        Film saved = storage.addFilm(film);

        saved.setName("Updated Film");

        storage.updateFilm(saved);

        Optional<Film> updated = storage.getFilmById(saved.getId());

        assertTrue(updated.isPresent());
        assertEquals("Updated Film", updated.get().getName());
    }

    @Test
    void shouldDeleteFilm() {
        Film saved = storage.addFilm(film);

        storage.deleteFilm(saved.getId());

        Optional<Film> found = storage.getFilmById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldGetAllFilms() {
        storage.addFilm(film);

        Film film2 = new Film();
        film2.setName("Film B");
        film2.setDescription("Another film");
        film2.setReleaseDate(LocalDate.of(2001, 1, 1));
        film2.setDuration(90);
        film2.setMpa(MpaRating.PG);
        film2.setGenres(Set.of(Genre.ACTION));

        storage.addFilm(film2);

        List<Film> films = (List<Film>) storage.getFilms();

        assertEquals(2, films.size());
    }

    @Test
    void shouldAddLikeAndReturnTopFilms() {
        Film saved = storage.addFilm(film);

        User user = createUser();

        storage.addLikeToFilm(saved.getId(), user.getId());

        List<Film> top = storage.getTopFilmsList(10);

        assertFalse(top.isEmpty());
        assertEquals(saved.getId(), top.get(0).getId());
    }

    @Test
    void shouldDeleteLike() {
        Film saved = storage.addFilm(film);

        User user = createUser();

        storage.addLikeToFilm(saved.getId(), user.getId());
        storage.deleteLike(saved.getId(), user.getId());

        List<Film> top = storage.getTopFilmsList(10);

        // фильм должен быть, но без лайков
        assertEquals(1, top.size());

        Film result = top.get(0);

        // лайков нет → он не должен подняться в топе
        assertEquals(saved.getId(), result.getId());
    }

    @Test
    void shouldReturnTopFilmsByLikes() {
        Film f1 = storage.addFilm(film);

        Film f2 = new Film();
        f2.setName("Top Film");
        f2.setDescription("Best");
        f2.setReleaseDate(LocalDate.of(2000, 1, 1));
        f2.setDuration(100);
        f2.setMpa(MpaRating.G);
        f2.setGenres(Set.of(Genre.ACTION));

        f2 = storage.addFilm(f2);

        User u1 = createUser();
        User u2 = createUser();

        storage.addLikeToFilm(f2.getId(), u1.getId());
        storage.addLikeToFilm(f2.getId(), u2.getId());

        List<Film> top = storage.getTopFilmsList(1);

        assertEquals(1, top.size());
        assertEquals(f2.getId(), top.get(0).getId());
    }

    @Test
    void shouldGetFilmsByMpa() {
        storage.addFilm(film);

        List<Film> films = (List<Film>) storage.getFilmsByMpaId(MpaRating.G.getId());

        assertFalse(films.isEmpty());
    }

    @Test
    void shouldGetFilmsByGenre() {
        storage.addFilm(film);

        List<Film> films = (List<Film>) storage.getFilmsByGenreId(Genre.COMEDY.getId());

        assertFalse(films.isEmpty());
    }
}