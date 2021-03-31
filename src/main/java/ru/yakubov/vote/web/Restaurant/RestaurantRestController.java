package ru.yakubov.vote.web.Restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakubov.vote.model.Restaurants;

import java.util.List;

import static ru.yakubov.vote.web.RestUrlPattern.RESTAURANT_REST_URL;
import static ru.yakubov.vote.web.RestUrlPattern.ROOT_REST_URL;

/*
 * GET /restaurant             get all restaurants
 * GET /restaurant/{id}        get restaurant
 * */

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractRestaurantRestController {
    public static final String REST_URL = ROOT_REST_URL + RESTAURANT_REST_URL;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurants get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurants> getAll() {
        return service.getAll();
    }
}
