package ru.yakubov.vote.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.controller.AbstractControllerTest;
import ru.yakubov.vote.controller.json.JsonUtil;
import ru.yakubov.vote.model.User;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.datajpa.DataJpaVoteRepository;
import ru.yakubov.vote.service.UserService;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.UserTo;
import ru.yakubov.vote.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;
import static ru.yakubov.vote.controller.RestUrlPattern.VOTES_URL;
import static ru.yakubov.vote.controller.RestUrlPattern.VOTE_URL;
import static ru.yakubov.vote.controller.json.JsonUtil.writeValue;
import static ru.yakubov.vote.service.VoteService.VOTE_DEADLINE;
import static ru.yakubov.vote.util.VoteUtilsTo.createTo;

class ProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileRestController.REST_URL;

    @Autowired
    UserService service;

    @Autowired
    @Lazy
    VoteService voteService;

    @Autowired
    @Lazy
    DataJpaVoteRepository voteRepository;

    @Autowired
    CacheManager cacheManager;

    //GET /rest/profile                                           get user profile by id
    @Test
    void get() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.user2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        User getUser = TestUtil.readFromJsonResultActions(actions, User.class);
        USER_MATCHER.assertMatch(getUser, UserTestData.user2);
    }

    //GET /rest/profile/vote                                            get current user vote
    @Test
    void getCurrentVote() throws Exception {

        Votes vote = new Votes(LocalDate.now());
        vote.setRestaurant(RestaurantTestData.restaurant3);
        vote.setUser(UserTestData.user1);

        VoteTo create = voteService.create(vote);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTE_URL)
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(create)));

    }

    //GET /rest/profile/votes/in?beginDate=2021-03-08&endDate=2021-03-10     get user vote by date (period)
    @Test
    void getUserByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTES_URL + "/in?beginDate=2021-03-08&endDate=2021-03-10")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //PUT /rest/profile                    update
    @Test
    void update() throws Exception {
        User user = new User(UserTestData.user1);
        user.setName("Update NAME");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createTo(user)))
                .with(userHttpBasic(UserTestData.user1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(UserTestData.USER_ID1).getName(), "Update NAME");
    }

    //POST /rest/profile/register                         register new user
    @Test
    void createRegisterWithLocation() throws Exception {
        UserTo userTo = new UserTo("UserNew", "email@email.com", "password");
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJsonResultActions(actions, User.class);
        Integer id = created.getId();
        User user = service.get(id);

        assertEquals(user.getName(), userTo.getName());
        assertEquals(user.getEmail(), userTo.getEmail());
    }
}