package ru.yakubov.vote.web.menu;

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
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.util.exception.NotFoundException;
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.MenuTestData.MENU9;
import static ru.yakubov.vote.MenuTestData.MENU_MATCHER;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.web.json.JsonUtil.writeValue;

public class AdminMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuRestController.REST_URL;

    @Autowired
    MenuService service;

    //GET    /admin/menu/one/{id}                                 get menu item
    @Test
    void getOneMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/one/" + MENU9.getId())
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(MENU9)));
    }

    //GET    /admin/menu/{id}                                     get all menu items of restaurant
    @Test
    void getAllByRestaurantId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + RestaurantTestData.RESTAURANT_ID1)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

    }

    //GET    /admin/menu/{id}/in?date1={date1}&date2={date2}      get all menu items of restaurant from date
    @Test
    void GetAllByRestaurantIdAndDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/50005/in?date1=2021-01-01&&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET    /admin/menu/all/in?date1={date1}&date2={date2}       get menu for all restaurants for the period
    @Test
    void GetAllByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/all/in?date1=2021-01-01&&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //POST   /admin/menu/                                         create menu item
    @Test
    void createMenuWithLocation() throws Exception {
        Menu menu = MenuTestData.NEW_MENU1;
        menu.setId(null);
        menu.setRestaurant(RestaurantTestData.restaurant4);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menu))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = TestUtil.readFromJsonResultActions(actions, Menu.class);
        Integer id = created.getId();
        menu.setId(id);
        MENU_MATCHER.assertMatch(created, menu);
        MENU_MATCHER.assertMatch(service.get(id), menu);
    }

    //DELETE /admin/menu/{id}                                     delete menu item
    @Test
    void deleteOneMenu() throws Exception {

        assertNotNull(service.get(MenuTestData.MENU1.getId()));

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + MenuTestData.MENU1.getId())
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(MenuTestData.MENU1.getId()));
    }

    //PUT    /admin/menu/{id}                                     update menu item
    @Test
    void update() throws Exception {
        MENU9.setDecription("Update Description");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU9.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU9))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(MENU9.getId()).getDecription(), "Update Description");

    }


}
