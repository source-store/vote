package ru.yakubov.vote.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.repository.RestaurantRepository;

import java.util.List;

@Repository
public class JdbcRestaurantRepository implements RestaurantRepository {

    private static final BeanPropertyRowMapper<Restaurants> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Restaurants.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcRestaurantRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("restaurants").usingGeneratedKeyColumns("id");;
    }

    @Override
    public Restaurants save(Restaurants restaurants) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(restaurants);

        if (restaurants.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            restaurants.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE restaurants SET name=:name, address=:address WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        return restaurants;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM restaurants WHERE id=?", id) != 0;
    }

    @Override
    public Restaurants get(int id) {
        List<Restaurants> restaurants = jdbcTemplate.query("SELECT * FROM restaurants WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(restaurants);
    }

    @Override
    public List<Restaurants> getAll() {
        List<Restaurants> rest = jdbcTemplate.query("SELECT * FROM restaurants ORDER BY name", ROW_MAPPER);
        return rest;
    }

    @Override
    public Integer createId() {
        return null;
    }
}
