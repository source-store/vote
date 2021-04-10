package ru.yakubov.vote.controller.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.TestUtil;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.controller.AbstractControllerTest;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.controller.RestUrlPattern.VOTE_URL;
import static ru.yakubov.vote.service.VoteService.VOTE_DEADLINE;


/*
 * @autor Alexandr.Yakubov
 **/

class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL;

    @Autowired
    @Lazy
    VoteService voteService;


    // /rest/votes                                         get result vote current date
    @Test
    void getResultCurdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }


    // /rest/votes/in?beginDate=YYYY-MM-DD&endDate=YYYY-MM-DD    get result vote by period
    @Test
    void getResultDatePeriod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/in?beginDate=2021-03-08&endDate=2021-03-11")
                .with(userHttpBasic(UserTestData.user3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }


    //POST /rest/votes?restaurantid={restaurantId}                         vote
    @Test
    void createVoteWithLocation() throws Exception {

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantid=" + RestaurantTestData.RESTAURANT_ID4)
                .contentType(APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.user1)))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = TestUtil.readFromJsonResultActions(actions, VoteTo.class);
        Integer id = created.getId();
        Votes getVotes = voteService.get(id);

        assertEquals(getVotes.getUser().getId(), UserTestData.user1.getId());
        assertEquals(getVotes.getRestaurant().getId(), RestaurantTestData.RESTAURANT_ID4);
    }

    //POST /rest/votes?restaurantid={restaurantId}                         vote
    @Test
    void updateVoteWithLocation() throws Exception {

        Votes vote = new Votes(LocalDate.now());
        vote.setRestaurant(RestaurantTestData.restaurant3);
        vote.setUser(UserTestData.user1);
        VoteTo createService = voteService.create(vote);

        if (LocalTime.now().isAfter(VOTE_DEADLINE)) {
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantid=" + RestaurantTestData.RESTAURANT_ID4)
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




}