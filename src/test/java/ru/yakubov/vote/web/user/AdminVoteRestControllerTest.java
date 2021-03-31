package ru.yakubov.vote.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.util.exception.NotFoundException;
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;
import static ru.yakubov.vote.web.RestUrlPattern.*;

class AdminVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteRestController.REST_URL;

    @Autowired
    UserVoteService service;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    public void setUp(){
        cacheManager.getCache("users").clear();
    }

    //GET /rest/admin/profiles                                                    get all user profiles
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+PROFILE_REST_URL)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET /rest/admin/profiles/{userId}                                           get user profile by id
    @Test
    void get() throws Exception {

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+PROFILE_REST_URL + "/" + UserTestData.USER_ID2)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk());

        UserVote created = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        USER_MATCHER.assertMatch(created, UserTestData.user2);

    }

    //GET /rest/admin/profiles/in?email=user2@yandex.ru                                get profile by email
    @Test
    void getByMail() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL +PROFILE_REST_URL+ "/in?email=" + UserTestData.admin2.getEmail())
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        UserVote getUserVote = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        USER_MATCHER.assertMatch(getUserVote, UserTestData.admin2);

    }

    //POST /rest/admin/profile                                                   create new user from UserVote
    @Test
    void createWithLocation() throws Exception {
        UserVote userVote = new UserVote(UserTestData.newUser);
        userVote.setId(null);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+PROFILE_REST_URL)
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

    //DELETE /rest/admin/profiles/{userId}                                        delete user
    @Test
    void delete() throws Exception {
        assertNotNull(service.get(UserTestData.USER_ID3));

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL +PROFILE_REST_URL+ "/" + UserTestData.USER_ID3)
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(UserTestData.USER_ID3));
    }

    //PUT /rest/admin/profiles/{userId}                                           update user
    @Test
    void update() throws Exception {
        UserVote userVote = new UserVote(UserTestData.user1);
        userVote.setName("Update NAME");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL +PROFILE_REST_URL+ "/" + UserTestData.USER_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userVote))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(UserTestData.USER_ID1).getName(), "Update NAME");
    }

}