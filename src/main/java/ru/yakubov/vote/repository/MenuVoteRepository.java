package ru.yakubov.vote.repository;

import ru.yakubov.vote.model.Menu;

import java.util.List;

public interface MenuVoteRepository {

    Menu save(Menu menu);

    // false if not found
    boolean delete(int id);

    // null if not found
    Menu get(int id);

    List<Menu> getAll();

    List<Menu> getAllByRestaurantId(int restaurantId);

    Integer getIdRestaurant(int id);

}
