package ru.yakubov.vote.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.web.AbstractControllerTest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.MenuTestData.MENU9;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.web.json.JsonUtil.writeValue;


public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL;

    @Autowired
    MenuService service;

    //GET    /menu/all/in?date1={date1}&date2={date2}       get menu for all restaurants for the period
    @Test
    void GetAllByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/all/in?date1=2021-01-01&&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET    /menu/{id}/in?date1={date1}&date2={date2}      get all menu items of restaurant from date
    @Test
    void GetAllByRestaurantIdAndDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/50005/in?date1=2021-01-01&&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET    /menu/{id}                                     get all menu items of restaurant
    @Test
    void getAllByRestaurantId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + RestaurantTestData.RESTAURANT_ID1)
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

    }

}
