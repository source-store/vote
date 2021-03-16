 package ru.yakubov.vote.web.Restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.util.VoteUtilsTo;

import java.net.URI;
import java.util.List;

public class AdminRestaurantRestController extends AbstractRestaurantRestController{
    public static final String REST_URL = "/admin/restaurant";

    @GetMapping(value = "/to", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAllTo() {
        return VoteUtilsTo.getRestaurantTos(service.getAll());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurants> getAll() {
        return service.getAll();
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
    public ResponseEntity<Restaurants>  createWithLocations(Restaurants restaurant) {
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
