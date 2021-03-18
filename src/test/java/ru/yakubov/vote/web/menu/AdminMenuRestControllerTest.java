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
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.json.JsonUtil;

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

    @Test
    void getOneMenu() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/one/50017")
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(MENU9)));

    }

    @Test
    void getRestaurantMenu() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/"+ RestaurantTestData.RESTAURANT_ID1)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

    }

    @Test
    void GetOneRestaurantByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL +"/50005/in?date1=2021-01-01&&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    void GetAllByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL +"/all/in?date1=2021-01-01&&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    void createMenuWithLocation() throws Exception {
        Menu menu = MenuTestData.NEW_MENU1;
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


}
