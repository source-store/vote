package ru.yakubov.vote.web.Restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.util.VoteUtilsTo;

import java.util.List;



/*
 * GET /restaurant             get all restaurants
 * GET /restaurant/to          get all restaurantsTo
 * GET /restaurant/{id}        get restaurant
 * GET /restaurant/to/{id}     get restaurantTo
 * */



@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController extends AbstractRestaurantRestController{
    public static final String REST_URL = ROOT_REST_URL+RESTAURANT_REST_URL;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurants get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping(value = "/to/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo getTo(@PathVariable int id) {
        return VoteUtilsTo.createTo(service.get(id));
    }

    @GetMapping(value = "/to", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAllTo() {
        return VoteUtilsTo.getRestaurantTos(service.getAll());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurants> getAll() {
        return service.getAll();
    }
}
