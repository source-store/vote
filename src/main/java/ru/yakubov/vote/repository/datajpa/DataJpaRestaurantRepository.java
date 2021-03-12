package ru.yakubov.vote.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.repository.RestaurantRepository;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaRestaurantRepository implements RestaurantRepository {

    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }


    @Override
//    @Transactional
    public Restaurants save(Restaurants restaurants) {
        return crudRestaurantRepository.save(restaurants);
    }

    @Override
//    @Transactional
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    public Restaurants get(int id) {
        return crudRestaurantRepository.getOne(id);
    }

    @Override
    public List<Restaurants> getAll() {
        return crudRestaurantRepository.findAll();
    }

    @Override
//    @Transactional
    public Integer createId() {
        return null;
    }

}
