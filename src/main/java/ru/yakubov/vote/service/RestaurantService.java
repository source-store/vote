package ru.yakubov.vote.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.repository.RestaurantRepository;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {


    private final RestaurantRepository repository;


    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    //    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurants create(Restaurants restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    //    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public Restaurants get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    //    @Cacheable("restaurants")
    @Transactional
    public List<Restaurants> getAll() {
        return repository.getAll();
    }

    //    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurants restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }


}
