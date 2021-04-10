package ru.yakubov.vote.controller.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.service.RestaurantService;

import java.util.List;

public abstract class AbstractRestaurantRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService service;

    public List<Restaurants> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Restaurants get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }
}
