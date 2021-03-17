package ru.yakubov.vote.web.Restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.service.RestaurantService;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.web.RestUrlPattern;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantRestController extends RestUrlPattern {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService service;


    public List<RestaurantTo> getAllTo() {
        log.info("getAllTo");
        return VoteUtilsTo.getRestaurantTos(service.getAll());
    }


    public List<Restaurants> getAll() {
        log.info("getAll");
        return service.getAll();
    }


    public Restaurants get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public RestaurantTo getTo(int id) {
        log.info("getTo {}", id);
        return VoteUtilsTo.createTo(service.get(id));
    }


    public Restaurants create(Restaurants restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Restaurants restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }


}
