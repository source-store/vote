package ru.yakubov.vote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.util.exception.NotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yakubov.vote.RestaurantTestData.RESTAURANT_MATCHER;

public class RestaurantServiceTest extends AbstractTest {
    @Autowired
    protected RestaurantService service;

    @Test
    public void create() {
        Restaurants created = service.create(RestaurantTestData.new_restaurant);
        int id = created.getId();
        RestaurantTestData.new_restaurant.setId(id);
        RESTAURANT_MATCHER.assertMatch(service.get(id), RestaurantTestData.new_restaurant);
    }

    @Test
    public void get() {
        RESTAURANT_MATCHER.assertMatch(service.get(RestaurantTestData.RESTAURANT_ID1), RestaurantTestData.restaurant1);
    }

    @Test
    public void getAll() {
        Set<Restaurants> restaurants1 = new HashSet<Restaurants>();
        restaurants1.add(RestaurantTestData.restaurant1);
        restaurants1.add(RestaurantTestData.restaurant2);
        restaurants1.add(RestaurantTestData.restaurant3);
        restaurants1.add(RestaurantTestData.restaurant4);
        Set<Restaurants> restaurants2 = new HashSet<Restaurants>(service.getAll());
        RESTAURANT_MATCHER.assertMatch(restaurants1, restaurants2);
    }

    @Test
    public void delete() {
        assertNotNull(service.get(RestaurantTestData.RESTAURANT_ID1));
        service.delete(RestaurantTestData.RESTAURANT_ID1);
        assertThrows(NotFoundException.class, () -> service.get(RestaurantTestData.RESTAURANT_ID1));
    }

}
