 package ru.yakubov.vote.web.Restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.web.View;
import ru.yakubov.vote.web.menu.AdminMenuRestController;

import java.net.URI;
import java.util.List;

/*
* GET /admin/restaurant             get all restaurants
* GET /admin/restaurant/to          get all restaurantsTo
* GET /admin/restaurant/{id}        get restaurant
* GET /admin/restaurant/to/{id}     get restaurantTo
* POST /admin/restaurant            create restaurant
* DELETE /admin/restaurant/{id}     delete restaurant
* PUT /admin/restaurant             UPDATE restaurant
* */

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantRestController{
    public static final String REST_URL = ROOT_REST_URL + ADMIN_REST_URL + RESTAURANT_REST_URL;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurants> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/to", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAllTo() {
        return VoteUtilsTo.getRestaurantTos(service.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurants get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping(value = "/to/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo getTo(@PathVariable int id) {
        return VoteUtilsTo.createTo(service.get(id));
    }


    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurants>  createWithLocations(@Validated(View.Web.class) @RequestBody Restaurants restaurant) {
        Restaurants newRestaurant = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL+"/{Id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(int id) {
        super.delete(id);
    }

    @PutMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Restaurants restaurant) {
        super.update(restaurant, restaurant.getId());
    }
}
