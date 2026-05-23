package ru.yandex.practicum.filmorate.dao.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public class MpaDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Mpa> getMpa() {
        String sql = """
                SELECT *
                FROM mpa
                """;

        return jdbcTemplate.query(sql, new MpaRowMapper());
    }

    public Mpa getMpaById(long id) {
        String sql = """
                SELECT *
                FROM mpa 
                WHERE mpaId = ?
                """;

        return jdbcTemplate.queryForObject(sql, new MpaRowMapper(), id);
    }
}
