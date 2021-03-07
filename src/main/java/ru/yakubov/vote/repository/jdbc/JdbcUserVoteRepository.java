package ru.yakubov.vote.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.UserVoteRepository;

import java.util.List;

@Repository
public class JdbcUserVoteRepository implements UserVoteRepository {

    private static final BeanPropertyRowMapper<UserVote> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserVote.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public JdbcUserVoteRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");;
    }

    @Override
    public UserVote save(UserVote userVote) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(userVote);

        if (userVote.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            userVote.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        return userVote;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public UserVote get(int id) {
        List<UserVote> usersVote = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(usersVote);
    }

    @Override
    public UserVote getByEmail(String email) {
        List<UserVote> userVotes = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(userVotes);
    }

    @Override
    public List<UserVote> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }
}
