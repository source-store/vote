package ru.yakubov.vote.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.service.RestaurantService;
import ru.yakubov.vote.util.exception.NotFoundException;
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.Restaurant.AdminRestaurantRestController;
import ru.yakubov.vote.web.json.JsonUtil;
import ru.yakubov.vote.web.menu.AdminMenuRestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.MenuTestData.MENU9;
import static ru.yakubov.vote.MenuTestData.MENU_MATCHER;
import static ru.yakubov.vote.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.web.json.JsonUtil.writeValue;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.REST_URL;

    @Autowired
    RestaurantService service;

    //GET /rest/admin/restaurant             get all restaurants
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET /rest/admin/restaurant/{id}        get restaurant
    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"/"+RestaurantTestData.RESTAURANT_ID2)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(RestaurantTestData.restaurant2)));

    }

    //POST /rest/admin/restaurant            create restaurant
    @Test
    void createWithLocations() throws Exception {

        Restaurants newRestaurants = RestaurantTestData.new_restaurant;
        newRestaurants.setId(null);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurants))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurants created = TestUtil.readFromJsonResultActions(actions, Restaurants.class);
        Integer id = created.getId();
        newRestaurants.setId(id);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurants);
        RESTAURANT_MATCHER.assertMatch(service.get(id), newRestaurants);
    }

    //DELETE /rest/admin/restaurant/{id}     delete restaurant
    @Test
    void delete() throws Exception {
        assertNotNull(service.get(RestaurantTestData.RESTAURANT_ID1));

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RestaurantTestData.RESTAURANT_ID1)
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(RestaurantTestData.RESTAURANT_ID1));
    }

    //PUT /rest/admin/restaurant             UPDATE restaurant
    @Test
    void update() throws Exception {
        Restaurants restaurants = RestaurantTestData.restaurant3;
        restaurants.setName("Update NAME");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurants))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(RestaurantTestData.RESTAURANT_ID3).getName(), "Update NAME");
    }
}