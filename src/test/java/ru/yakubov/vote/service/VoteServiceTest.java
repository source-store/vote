package ru.yakubov.vote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.VoteTestData;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yakubov.vote.VoteTestData.VOTE_MATCHER;

public class VoteServiceTest extends AbstractTest {

    @Autowired
    protected VoteService service;

    private static final LocalDate startDate = LocalDate.of(2021, 3, 8);
    private static final LocalDate endDate = LocalDate.of(2021, 3, 9);

    @Test
    void get() {
        VOTE_MATCHER.assertMatch(service.get(VoteTestData.VOTE_ID1), VoteTestData.VOTE1);
    }

    @Test
    void delete() {
        assertNotNull(service.get(VoteTestData.VOTE_ID1));
        service.delete(VoteTestData.VOTE_ID1);
        assertThrows(NotFoundException.class, () -> service.get(VoteTestData.VOTE_ID1));
    }

    @Test
    void create() {
        VoteTestData.NEW_VOTE.setUserVote(UserTestData.user1);
        VoteTestData.NEW_VOTE.setRestaurant(RestaurantTestData.restaurant1);
        VoteTo created = service.create(VoteTestData.NEW_VOTE);
        int id = created.getId();
        VoteTestData.NEW_VOTE.setId(id);
        VOTE_MATCHER.assertMatch(service.get(id), VoteTestData.NEW_VOTE);
    }

    @Test
    void getByUserDate() {
        VOTE_MATCHER.assertMatch(service.getByUserDate(UserTestData.USER_ID3, startDate, endDate), VoteTestData.VOTE3);
    }

    @Test
    void getByUserOneDate() {
        VOTE_MATCHER.assertMatch(service.getByUserOneDate(UserTestData.USER_ID1, startDate), VoteTestData.VOTE1);
    }


}
