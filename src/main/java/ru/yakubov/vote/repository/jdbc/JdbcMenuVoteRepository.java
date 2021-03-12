package ru.yakubov.vote.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.MenuVoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcMenuVoteRepository implements MenuVoteRepository {

    private static final BeanPropertyRowMapper<Menu> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Menu.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcMenuVoteRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("menu").usingGeneratedKeyColumns("id");
    }


    @Override
    public Menu save(Menu menu) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(menu);

        if (menu.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            menu.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE menu SET date=:date, decription=:decription, price=:price WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        return menu;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM menu WHERE id=?", id) != 0;
    }

    @Override
    public Menu get(int id) {
        List<Menu> menu = jdbcTemplate.query("SELECT * FROM menu WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(menu);
    }


    @Override
    public List<Menu> getAll() {
        return jdbcTemplate.query("SELECT * FROM menu ORDER BY date, restaurant_id, decription", ROW_MAPPER);
    }

    @Override
    public List<Menu> getAllByRestaurantId(int restaurantId) {
        List<Menu> menu = jdbcTemplate.query("SELECT * FROM menu WHERE restaurant_id=? ORDER BY date, decription", ROW_MAPPER, restaurantId);
        System.out.println(menu.get(0).getRestaurant().toString());
        return menu;
        //        return jdbcTemplate.query("SELECT * FROM menu WHERE restaurant_id=? ORDER BY date, decription", ROW_MAPPER, restaurantId);
    }

    @Override
    public Integer getIdRestaurant(int id) {
        List<Menu> menu = jdbcTemplate.query("SELECT * FROM menu WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(menu).getRestaurant().getId();
    }

    @Override
    public List<Menu> GetAllByDate(LocalDate beginDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate beginDate, LocalDate endDate) {
        return null;
    }

}
