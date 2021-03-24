package ru.yakubov.vote.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.VoteTestData;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.datajpa.CrudVoteRepository;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.exception.NotFoundException;
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;
import static ru.yakubov.vote.model.Votes.VOTE_DEADLINE;
import static ru.yakubov.vote.util.VoteUtilsTo.createTo;

class ProfileVoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileVoteRestController.REST_URL;

    @Autowired
    UserVoteService service;

    @Autowired
    @Lazy
    VoteService voteService;

    @Autowired
    @Lazy
    private CrudVoteRepository crudRepository;


    //GET /result                                         get result vote current date
    @Test
    void getResultCurdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"/result")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET /result/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
    @Test
    void getResultDatePeriod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"/result/in?date1=2021-03-08&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    //GET /rest/profile                                           get user profile by id
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

    //PUT /profile                    update
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

    //POST /profile/{restaurantId}    vote
    @Test
    void createVoteWithLocation() throws Exception {

        if (LocalTime.now().isAfter(VOTE_DEADLINE)) {
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + RestaurantTestData.RESTAURANT_ID4)
                    .contentType(APPLICATION_JSON)
                    .with(userHttpBasic(UserTestData.user1)))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }else {
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+"/"+ RestaurantTestData.RESTAURANT_ID4)
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

    }

    //DELETE /profile                 delete current user vote
    @Test
    void voteDelete() throws Exception {

        Votes votes = new Votes(VoteTestData.VOTE1);
        votes.setDate(LocalDate.now());
        votes.setUserVote(UserTestData.user2);

        Votes newVote = crudRepository.save(votes);

        assertNotNull(voteService.get(newVote.getId()));

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(UserTestData.user2)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> voteService.get(newVote.getId()));
    }
}