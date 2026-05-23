package ru.yandex.practicum.filmorate.dao.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class FilmWithGenreRowMapper implements RowMapper<Film> {

        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

            Film film = new Film();

            film.setId(rs.getLong("film_id"));
            film.setName(rs.getString("film_name"));

            Set<Genre> genres = new HashSet<>();

            long genreId = rs.getLong("genre_id");

            if (!rs.wasNull()) {
                Genre genre = new Genre();
                genre.setId(genreId);
                genre.setName(rs.getString("genre_name"));

                genres.add(genre);
            }

            film.setGenres(genres);

            return film;
        }
}

