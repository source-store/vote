package ru.yakubov.vote.web.Restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.web.View;

import java.net.URI;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;
import static ru.yakubov.vote.web.RestUrlPattern.*;

/**
 * GET /rest/admin/restaurants             get all restaurants
 * GET /rest/admin/restaurants/{id}        get restaurant
 * POST /rest/admin/restaurants            create restaurant
 * DELETE /rest/admin/restaurants/{id}     delete restaurant
 * PUT /rest/admin/restaurants             UPDATE restaurant
 */

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantRestController {
    public static final String REST_URL = ROOT_REST_URL + ADMIN_REST_URL + RESTAURANT_REST_URL;

    // /rest/admin/restaurants             get all restaurants
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurants> getAll() {
        return service.getAll();
    }

    // /rest/admin/restaurants/{id}        get restaurant
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurants get(@PathVariable int id) {
        return service.get(id);
    }

    // /rest/admin/restaurants            create restaurant
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurants> createWithLocations(@Validated(View.Web.class) @RequestBody Restaurants restaurant) {
        Restaurants newRestaurant = create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    // /rest/admin/restaurants/{id}     delete restaurant
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int id) {
        delete(id);
    }

    // /rest/admin/restaurants             UPDATE restaurant
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id,
                       @Validated(View.Web.class) @RequestBody Restaurants restaurant) {
        update(restaurant, id);
    }

    private Restaurants create(Restaurants restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.create(restaurant);
    }

    private void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    private void update(Restaurants restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }


}
