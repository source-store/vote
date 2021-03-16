package ru.yakubov.vote.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Test
//    @WithAnonymousUser
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(MenuRestController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
//    @WithAnonymousUser
    void GetAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(MenuRestController.REST_URL+"/all/in?date1=2021-03-10&&date2=2021-03-11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
//    @WithAnonymousUser
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(MenuRestController.REST_URL+"/50005"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
//    @WithAnonymousUser
    void GetAllByRestaurantIdAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(MenuRestController.REST_URL+"/50005/in?date1=2021-03-10&&date2=2021-03-11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
