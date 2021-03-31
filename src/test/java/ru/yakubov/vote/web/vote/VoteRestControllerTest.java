package ru.yakubov.vote.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.web.AbstractControllerTest;
import ru.yakubov.vote.web.user.ProfileVoteRestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yakubov.vote.TestUtil.userHttpBasic;
import static ru.yakubov.vote.web.RestUrlPattern.RESULT_VOTE_REST_URL;


/*
 * @autor Alexandr.Yakubov
 **/

class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL;

    // /rest/votes                                         get result vote current date
    @Test
    void getResultCurdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }


    // /rest/votes/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
    @Test
    void getResultDatePeriod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/in?date1=2021-03-08&date2=2021-03-11")
                .with(userHttpBasic(UserTestData.user3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

}