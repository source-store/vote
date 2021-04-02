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
import ru.yakubov.vote.controller.user.ProfileVoteRestController;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.VoteRepository;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.controller.AbstractControllerTest;
import ru.yakubov.vote.controller.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;
import static ru.yakubov.vote.service.VoteService.VOTE_DEADLINE;
import static ru.yakubov.vote.util.VoteUtilsTo.createTo;
import static ru.yakubov.vote.controller.RestUrlPattern.VOTES_URL;
import static ru.yakubov.vote.controller.RestUrlPattern.VOTE_URL;
import static ru.yakubov.vote.controller.json.JsonUtil.writeValue;

class ProfileVoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileVoteRestController.REST_URL;

    @Autowired
    UserVoteService service;

    @Autowired
    @Lazy
    VoteService voteService;

    @Autowired
    @Lazy
    VoteRepository voteRepository;

    @Autowired
    CacheManager cacheManager;

    //GET /rest/profiles                                           get user profile by id
    @Test
    void get() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.user2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        UserVote getUserVote = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        USER_MATCHER.assertMatch(getUserVote, UserTestData.user2);
    }

    //GET /rest/profiles/vote                                            get current user vote
    @Test
    void getCurrentVote() throws Exception {

        Votes vote = new Votes(LocalDate.now());
        vote.setRestaurant(RestaurantTestData.restaurant3);
        vote.setUserVote(UserTestData.user1);

        VoteTo create = voteService.create(vote);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTE_URL)
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(writeValue(create)));

    }

    //GET /rest/profiles/votes/in?date1=2021-03-08&date2=2021-03-10     get user vote by date (period)
    @Test
    void getUserVoteByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTES_URL + "/in?date1=2021-03-08&date2=2021-03-10")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //PUT /rest/profiles                    update
    @Test
    void update() throws Exception {
        UserVote userVote = new UserVote(UserTestData.user1);
        userVote.setName("Update NAME");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createTo(userVote)))
                .with(userHttpBasic(UserTestData.user1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(service.get(UserTestData.USER_ID1).getName(), "Update NAME");
    }

    //POST /rest/profiles/{restaurantId}    vote
    @Test
    void createVoteWithLocation() throws Exception {

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + VOTE_URL + "?id=" + RestaurantTestData.RESTAURANT_ID4)
                .contentType(APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.user1)))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = TestUtil.readFromJsonResultActions(actions, VoteTo.class);
        Integer id = created.getId();
        Votes getVotes = voteService.get(id);

        assertEquals(getVotes.getUserVote().getId(), UserTestData.user1.getId());
        assertEquals(getVotes.getRestaurant().getId(), RestaurantTestData.RESTAURANT_ID4);
    }

    //POST /rest/profiles/{restaurantId}    vote
    @Test
    void updateVoteWithLocation() throws Exception {

        Votes vote = new Votes(LocalDate.now());
        vote.setRestaurant(RestaurantTestData.restaurant3);
        vote.setUserVote(UserTestData.user1);
        VoteTo createService = voteService.create(vote);

        if (LocalTime.now().isAfter(VOTE_DEADLINE)) {
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + VOTE_URL + "?id=" + RestaurantTestData.RESTAURANT_ID4)
                    .contentType(APPLICATION_JSON)
                    .with(userHttpBasic(UserTestData.user1)))
                    .andDo(print())
                    .andExpect(status().isConflict());

        } else {

            Votes getService = voteService.getByUserOneDate(UserTestData.USER_ID1, LocalDate.now());
            assertEquals(createService.getRestaurantId(), getService.getRestaurant().getId());

            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + VOTE_URL + "?id=" + RestaurantTestData.RESTAURANT_ID4)
                    .contentType(APPLICATION_JSON)
                    .with(userHttpBasic(UserTestData.user1)))
                    .andDo(print())
                    .andExpect(status().isCreated());

            VoteTo createRest = TestUtil.readFromJsonResultActions(actions, VoteTo.class);

            assertEquals(createService.getUserId(), createRest.getUserId());
            assertEquals(RestaurantTestData.RESTAURANT_ID4, createRest.getRestaurantId());
        }

    }


    //POST /rest/profiles/register                         register new user
    @Test
    void createRegisterWithLocation() throws Exception {
        UserVoteTo userVoteTo = new UserVoteTo("UserNew", "email@email.com", "password");
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(userVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserVote created = TestUtil.readFromJsonResultActions(actions, UserVote.class);
        Integer id = created.getId();
        UserVote userVote = service.get(id);

        assertEquals(userVote.getName(), userVoteTo.getName());
        assertEquals(userVote.getEmail(), userVoteTo.getEmail());
    }
}