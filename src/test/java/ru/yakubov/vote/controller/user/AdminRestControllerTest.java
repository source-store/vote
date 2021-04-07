package ru.yakubov.vote.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.controller.AbstractControllerTest;
import ru.yakubov.vote.controller.json.JsonUtil;
import ru.yakubov.vote.model.User;
import ru.yakubov.vote.service.UserService;
import ru.yakubov.vote.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;
import static ru.yakubov.vote.controller.RestUrlPattern.PROFILES_REST_URL;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL;

    @Autowired
    UserService service;

    @Autowired
    CacheManager cacheManager;

    //GET /rest/admin/profiles                                                    get all user profiles
    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + PROFILES_REST_URL)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET /rest/admin/profiles/{userId}                                           get user profile by id
    @Test
    void get() throws Exception {

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + PROFILES_REST_URL + "/" + UserTestData.USER_ID2)
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk());

        User created = TestUtil.readFromJsonResultActions(actions, User.class);
        USER_MATCHER.assertMatch(created, UserTestData.user2);

    }

    //GET /rest/admin/profiles/in?email=user2@yandex.ru                                get profile by email
    @Test
    void getByMail() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + PROFILES_REST_URL + "/in?email=" + UserTestData.admin2.getEmail())
                .with(userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        User getUser = TestUtil.readFromJsonResultActions(actions, User.class);
        USER_MATCHER.assertMatch(getUser, UserTestData.admin2);

    }

    //POST /rest/admin/profile                                                   create new user from User
    @Test
    void createWithLocation() throws Exception {
        User user = new User(UserTestData.newUser);
        user.setId(null);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + PROFILES_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJsonResultActions(actions, User.class);
        Integer id = created.getId();
        user.setId(id);
        USER_MATCHER.assertMatch(created, user);
        USER_MATCHER.assertMatch(service.get(id), user);
    }

    //DELETE /rest/admin/profiles/{userId}                                        delete user
    @Test
    void delete() throws Exception {
        assertNotNull(service.get(UserTestData.USER_ID3));

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + PROFILES_REST_URL + "/" + UserTestData.USER_ID3)
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(UserTestData.USER_ID3));
    }

    //PUT /rest/admin/profiles/{userId}                                           update user
    @Test
    void update() throws Exception {
        User user = new User(UserTestData.user1);
        user.setName("Update NAME");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + PROFILES_REST_URL + "/" + UserTestData.USER_ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user))
                .with(userHttpBasic(UserTestData.admin1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(UserTestData.USER_ID1).getName(), "Update NAME");
    }

}