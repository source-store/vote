package ru.yakubov.vote.web.Restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.service.RestaurantService;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.util.VoteUtils;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

@Controller
public class RestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return VoteUtils.getRestaurantTos(service.getAll());
    }

    public Restaurants get(int id) {
        log.info("get {}", id);
        return service.get(id);
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
