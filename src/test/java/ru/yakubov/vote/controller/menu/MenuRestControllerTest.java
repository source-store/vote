package ru.yakubov.vote.controller.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.controller.AbstractControllerTest;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.MenuTestData.MENU7;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.controller.json.JsonUtil.writeValue;


public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL;

    @Autowired
    MenuService service;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        Objects.requireNonNull(cacheManager.getCache("menus")).clear();
    }

    //GET    /rest/menus/today                                   get menu for today
    @Test
    void GetTodayMenus() throws Exception {

        Menu menu1 = new Menu(MenuTestData.NEW_MENU);
        Menu menu2 = new Menu(MenuTestData.NEW_MENU1);

        menu1.setId(null);
        menu2.setId(null);

        menu1.setDate(LocalDate.now());
        menu2.setDate(LocalDate.now());

        menu1.setRestaurant(RestaurantTestData.restaurant4);
        menu2.setRestaurant(RestaurantTestData.restaurant4);

        menu1 = new Menu(service.create(menu1));
        menu2 = new Menu(service.create(menu2));

        System.out.println(List.of(menu1, menu2));
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/today")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(List.of(menu1, menu2))));
    }

    //GET    /menus/in?beginDate={beginDate}&endDate={endDate}       get menu for all restaurants for the period
    @Test
    void GetAllByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/in?beginDate=2021-01-01&&endDate=2021-03-11")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET    /menus?id={id}&beginDate={beginDate}&endDate={endDate}      get all menu items of restaurant from date
    @Test
    void GetAllByRestaurantIdAndDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "?id=50005&beginDate=2021-01-01&&endDate=2021-03-11")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET    /menus/{id}                                 get menu item
    @Test
    void getOneMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + MENU7.getId())
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(MENU7)));
    }
}
