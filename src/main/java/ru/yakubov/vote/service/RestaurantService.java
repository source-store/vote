package ru.yakubov.vote.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.repository.datajpa.DataJpaRestaurantRepository;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final DataJpaRestaurantRepository repository;

    public RestaurantService(DataJpaRestaurantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Restaurants create(Restaurants restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurants get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Restaurants> getAll() {
        return repository.getAll();
    }

    @Transactional
    public void update(Restaurants restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }
}
