package ru.yakubov.vote.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.service.RestaurantService;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.util.exception.NotFoundException;
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.json.JsonUtil;
import ru.yakubov.vote.web.menu.AdminMenuRestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;
import static ru.yakubov.vote.web.json.JsonUtil.writeValue;

class AdminVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteRestController.REST_URL;

    @Autowired
    UserVoteService service;

    //GET /rest/admin/profile                                                    get all user profiles
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET /rest/admin/profile/{userId}                                           get user profile by id
    @Test
    void get() throws Exception {

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + UserTestData.USER_ID2)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk());

        UserVote created = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        USER_MATCHER.assertMatch(created, UserTestData.user2);

    }



    //POST /rest/admin/profile                                                   create new user from UserVote
    @Test
    void createWithLocation() throws Exception {
        UserVote userVote = UserTestData.newUser;
        userVote.setId(null);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userVote))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserVote created = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        Integer id = created.getId();
        userVote.setId(id);
        USER_MATCHER.assertMatch(created, userVote);
        USER_MATCHER.assertMatch(service.get(id), userVote);
    }

    //DELETE /rest/admin/profile/{userId}                                        delete user
    @Test
    void delete() throws Exception {
        assertNotNull(service.get(UserTestData.USER_ID3));

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + UserTestData.USER_ID3)
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(UserTestData.USER_ID3));
    }

    //PUT /rest/admin/profile/{userId}                                           update user
    @Test
    void update() throws Exception {
        UserVote userVote = UserTestData.user1;
        userVote.setName("Update NAME");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + UserTestData.USER_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userVote))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(UserTestData.USER_ID1).getName(), "Update NAME");
    }

    //GET /rest/admin/profiles/in?email=user2@yandex.ru                                get profile by email
    @Test
    void getByMail() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/in?email=" + UserTestData.admin2.getEmail())
                                .with(userHttpBasic(UserTestData.admin1)))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        UserVote getUserVote = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        USER_MATCHER.assertMatch(getUserVote, UserTestData.admin2);

    }

    //GET /rest/admin/profiles/{userId}}/vote/in?date1=2021-03-08&date2=2021-03-10     get user vote by date (period)
    @Test
    void getUserVoteByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"/"+UserTestData.USER_ID3+"/vote/in?date1=2021-03-08&date2=2021-03-10")
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }
}