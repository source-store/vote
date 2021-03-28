package ru.yakubov.vote.repository;

import ru.yakubov.vote.model.Restaurants;

import java.util.List;

public interface RestaurantRepository {
    // null if updated meal does not belong to userId
    Restaurants save(Restaurants restaurants);

    // false if meal does not belong to userId
    boolean delete(int id);

    // null if meal does not belong to userId
    Restaurants get(int id);

    // ORDERED dateTime desc
    List<Restaurants> getAll();
}
