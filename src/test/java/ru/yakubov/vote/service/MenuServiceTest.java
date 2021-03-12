package ru.yakubov.vote.service;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.AbstractTest;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static ru.yakubov.vote.MenuTestData.MENU_MATCHER;


public class MenuServiceTest extends AbstractTest {

    @Autowired
    protected MenuService service;

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    public void create() {
        Restaurants restaurants = restaurantService.create(RestaurantTestData.new_restaurant);
        MenuTestData.NEW_MENU.setId(null);
        MenuTestData.NEW_MENU.setRestaurant(restaurants);
        Menu menu = service.create(MenuTestData.NEW_MENU);
        MENU_MATCHER.assertMatch(service.get(menu.getId()), menu);
    }

    @Test
    public void get() {
        MENU_MATCHER.assertMatch(service.get(MenuTestData.MENU2.getId()), MenuTestData.MENU2);
    }

    @Test
    public void save() {
        Menu updatedMenu = service.get(MenuTestData.MENU2.getId());
        updatedMenu.setDecription("UpdateNote");
        service.create(updatedMenu);
        Menu menu = service.get(updatedMenu.getId());
        Assert.assertEquals(menu.getDecription(), "UpdateNote");
    }

    @Test
    public void getAll() {
        Assert.assertNotNull(service.getAll());
    }

    @Test
    public void getByIdRestaurant() {
        Restaurants restaurant = restaurantService.create(RestaurantTestData.new_restaurant);
        MenuTestData.NEW_MENU.setRestaurant(restaurant);
        MenuTestData.NEW_MENU1.setRestaurant(restaurant);
        Set<Menu> menu1 = new HashSet<Menu>();
        menu1.add(service.create(MenuTestData.NEW_MENU));
        menu1.add(service.create(MenuTestData.NEW_MENU1));

        Set<Menu> menu2 = new HashSet<Menu>(service.getAllByRestaurantId(restaurant.getId()));
        MENU_MATCHER.assertMatch(menu1, menu2);
    }

    @Test
    public void delete() {
        Assert.assertNotNull(service.get(MenuTestData.MENU1.getId()));
        service.delete(MenuTestData.MENU1.getId());
        assertThrows(NotFoundException.class, () -> service.get(MenuTestData.MENU1.getId()));
    }


    @Test
    public void GetAllByDate() {
        Restaurants restaurant = restaurantService.create(RestaurantTestData.new_restaurant);
        MenuTestData.NEW_MENU.setId(null);
        MenuTestData.NEW_MENU.setRestaurant(restaurant);
        MenuTestData.NEW_MENU1.setId(null);
        MenuTestData.NEW_MENU1.setRestaurant(restaurant);
        Set<Menu> menu1 = new HashSet<Menu>();
        menu1.add(service.create(MenuTestData.NEW_MENU));
        menu1.add(service.create(MenuTestData.NEW_MENU1));

        Set<Menu> menu2 = new HashSet<Menu>(service.GetAllByDate(LocalDate.of(2021, 1, 1),
                                                                 LocalDate.of(2021, 1, 10)));
        MENU_MATCHER.assertMatch(menu1, menu2);
    }

    @Test
    public void GetAllByRestaurantIdAndDate() {
        Restaurants restaurant = restaurantService.create(RestaurantTestData.new_restaurant);
        int idRestaurant = restaurant.getId();
        MenuTestData.NEW_MENU.setId(null);
        MenuTestData.NEW_MENU.setRestaurant(restaurant);
        MenuTestData.NEW_MENU1.setId(null);
        MenuTestData.NEW_MENU1.setRestaurant(restaurant);
        Set<Menu> menu1 = new HashSet<Menu>();
        menu1.add(service.create(MenuTestData.NEW_MENU));
        menu1.add(service.create(MenuTestData.NEW_MENU1));

        Set<Menu> menu2 = new HashSet<Menu>(service.GetAllByRestaurantIdAndDate(idRestaurant, LocalDate.of(2021, 1, 1),
                                                                                              LocalDate.of(2021, 1, 11)));
        MENU_MATCHER.assertMatch(menu1, menu2);
    }


}