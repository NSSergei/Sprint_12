package ru.yandex.practicum.filmorate.dao.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public class GenreDao {
    private JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Genre> getGenres() {
        String sql = """
                SELECT *
                FROM genre
                """;

        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

    public Genre getGenreById(long id) {
        String sql = """
                SELECT *
                FROM genre
                WHERE genreId = ?
                """;

        return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
    }
}
