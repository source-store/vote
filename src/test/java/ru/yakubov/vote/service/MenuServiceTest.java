package ru.yakubov.vote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yakubov.vote.MenuTestData.MENU_MATCHER;


public class MenuServiceTest extends AbstractTest {

    private static LocalDate startDate = LocalDate.of(2021, 1, 1);
    private static LocalDate endDate10 = LocalDate.of(2021, 1, 10);
    private static LocalDate endDate11 = LocalDate.of(2021, 1, 11);

    @Autowired
    protected MenuService service;

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    public void create() {
        Restaurants restaurants = new Restaurants(restaurantService.create(RestaurantTestData.new_restaurant));
        Menu menu = new Menu(MenuTestData.NEW_MENU);
        menu.setId(null);
        menu.setRestaurant(restaurants);

        Menu newMenu = service.create(menu);
        MENU_MATCHER.assertMatch(service.get(newMenu.getId()), menu);
    }

    @Test
    public void get() {
        MENU_MATCHER.assertMatch(service.get(MenuTestData.MENU2.getId()), MenuTestData.MENU2);
    }

    @Test
    public void save() {
        Menu updatedMenu = service.get(MenuTestData.MENU2.getId());
        updatedMenu.setDescription("UpdateNote");
        service.create(updatedMenu);
        assertEquals(service.get(updatedMenu.getId()).getDescription(), "UpdateNote");
    }

    @Test
    public void getAll() {
        assertNotNull(service.getAll());
    }

    @Test
    public void getByIdRestaurant() {

        Restaurants restaurant = restaurantService.create(RestaurantTestData.new_restaurant);
        Menu menu1 = new Menu(MenuTestData.NEW_MENU);
        Menu menu2 = new Menu(MenuTestData.NEW_MENU1);
        menu1.setRestaurant(restaurant);
        menu2.setRestaurant(restaurant);

        Set<Menu> menuSet1 = new HashSet<Menu>();

        menuSet1.add(service.create(menu1));
        menuSet1.add(service.create(menu2));

        Set<Menu> menuSet2 = new HashSet<Menu>(service.getAllByRestaurantId(restaurant.getId()));
        MENU_MATCHER.assertMatch(menuSet1, menuSet2);
    }

    @Test
    public void delete() {
        assertNotNull(service.get(MenuTestData.MENU1.getId()));
        service.delete(MenuTestData.MENU1.getId());
        assertThrows(NotFoundException.class, () -> service.get(MenuTestData.MENU1.getId()));
    }

    @Test
    public void GetAllByDate() {
        Restaurants restaurant = restaurantService.create(RestaurantTestData.new_restaurant);
        Menu menu1 = new Menu(MenuTestData.NEW_MENU);
        Menu menu2 = new Menu(MenuTestData.NEW_MENU1);

        menu1.setId(null);
        menu1.setRestaurant(restaurant);

        menu2.setId(null);
        menu2.setRestaurant(restaurant);

        Set<Menu> menuSet1 = new HashSet<Menu>();
        menuSet1.add(service.create(menu1));
        menuSet1.add(service.create(menu2));

        Set<Menu> menuSet2 = new HashSet<Menu>(service.GetAllByDate(startDate, endDate10));
        MENU_MATCHER.assertMatch(menuSet1, menuSet2);

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

        Set<Menu> menu2 = new HashSet<Menu>(service.GetAllByRestaurantIdAndDate(idRestaurant, startDate, endDate11));
        MENU_MATCHER.assertMatch(menu1, menu2);
    }


}