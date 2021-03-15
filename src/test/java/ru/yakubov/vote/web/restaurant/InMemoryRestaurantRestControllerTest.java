package ru.yakubov.vote.web.restaurant;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.AbstractInmemoryTest;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.repository.inmemory.InMemoryRestaurantRepository;
import ru.yakubov.vote.web.Restaurant.RestaurantController;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.yakubov.vote.RestaurantTestData.RESTAURANT_MATCHER;

public class InMemoryRestaurantRestControllerTest extends AbstractInmemoryTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRestaurantRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static RestaurantController controller;
    private static InMemoryRestaurantRepository repository;

    @BeforeEach
    public void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(RestaurantController.class);
        repository = appCtx.getBean(InMemoryRestaurantRepository.class);
    }

    @AfterEach
    public void afterClass() {
        appCtx.close();

    }

    @BeforeEach
    public void setup() {
        // re-initialize
        repository.init();
    }

    @Test
    public void get() {
        Restaurants restaurants = repository.get(RestaurantTestData.restaurant1.getId());
        RESTAURANT_MATCHER.assertMatch(restaurants, RestaurantTestData.restaurant1);
    }

    @Test
    public void save() {
        Restaurants restaurants = RestaurantTestData.restaurant1;
        restaurants.setAddress("TestAddress");
        repository.save(restaurants);
        Restaurants saveRestaurants = repository.get(restaurants.getId());
        RESTAURANT_MATCHER.assertMatch(saveRestaurants, restaurants);
    }

    @Test
    public void delete() {
        repository.delete(RestaurantTestData.restaurant1.getId());
        assertNull(repository.get(RestaurantTestData.restaurant1.getId()));
    }

    @Test
    public void getAll() {
        assertNotNull(repository.getAll());
    }


}
