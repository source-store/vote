package ru.yakubov.vote.repository;

import ru.yakubov.vote.model.Restaurants;

import java.util.List;

public interface RestaurantRepository {

    Restaurants save(Restaurants restaurants);

    boolean delete(int id);

    Restaurants get(int id);

    List<Restaurants> getAll();

    Restaurants getOne(int id);
}
