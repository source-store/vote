package ru.yakubov.vote.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Restaurants;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaRestaurantRepository {

    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurants save(Restaurants restaurants) {
        return crudRestaurantRepository.save(restaurants);
    }

    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    public Restaurants get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);// .getOne(id);
    }

    public Restaurants getOne(int id) {
        return crudRestaurantRepository.getOne(id);
    }

    public List<Restaurants> getAll() {
        return crudRestaurantRepository.findAll();
    }

}
