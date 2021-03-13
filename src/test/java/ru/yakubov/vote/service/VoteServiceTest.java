package ru.yakubov.vote.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.AbstractTest;
import ru.yakubov.vote.RestaurantTestData;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.VoteTestData;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static ru.yakubov.vote.VoteTestData.VOTE_MATCHER;

public class VoteServiceTest extends AbstractTest {

    @Autowired
    protected VoteService service;

    @Test
    public void get() {
        VOTE_MATCHER.assertMatch(service.get(VoteTestData.VOTE_ID1), VoteTestData.VOTE1);
    }

    @Test
    public void delete() {
        Assert.assertNotNull(service.get(VoteTestData.VOTE_ID1));
        service.delete(VoteTestData.VOTE_ID1);
        assertThrows(NotFoundException.class, () -> service.get(VoteTestData.VOTE_ID1));
    }

    @Test
    public void create() {
        VoteTestData.NEW_VOTE.setUserVote(UserTestData.user1);
        VoteTestData.NEW_VOTE.setRestaurant(RestaurantTestData.restaurant1);
        Votes created = service.create(VoteTestData.NEW_VOTE);
        int id = created.getId();
        VoteTestData.NEW_VOTE.setId(id);
        VOTE_MATCHER.assertMatch(service.get(id), VoteTestData.NEW_VOTE);
    }

    @Test
    public void getByRestaurant() {
        VOTE_MATCHER.assertMatch(service.getByRestaurant(RestaurantTestData.RESTAURANT_ID1), VoteTestData.VOTES1);
    }

    @Test
    public void getByRestaurantDate() {
        LocalDate startDate = LocalDate.of(2021, 3, 8);
        LocalDate endDate = LocalDate.of(2021, 3, 9);
        VOTE_MATCHER.assertMatch(service.getByRestaurantDate(RestaurantTestData.RESTAURANT_ID2, startDate, endDate), List.of(VoteTestData.VOTE3));

    }

    @Test
    public void getByUserDate() {
        LocalDate startDate = LocalDate.of(2021, 3, 8);
        LocalDate endDate = LocalDate.of(2021, 3, 9);
        VOTE_MATCHER.assertMatch(service.getByUserDate(UserTestData.USER_ID3, startDate, endDate), VoteTestData.VOTE3);
    }

    @Test
    public void getByUser() {
        Set<Votes> votes = new HashSet<Votes>(service.getByUser(UserTestData.USER_ID3));
        Set<Votes> votesTest = new HashSet<Votes>(List.of(VoteTestData.VOTE3, VoteTestData.VOTE4));
        VOTE_MATCHER.assertMatch(votes, votesTest);
    }

    @Test
    public void getByDate() {
        LocalDate startDate = LocalDate.of(2021, 3, 8);
        LocalDate endDate = LocalDate.of(2021, 3, 8);
        Set<Votes> votes = new HashSet<Votes>(service.getByDate(startDate, endDate));
        Set<Votes> votesTest = new HashSet<Votes>(List.of(VoteTestData.VOTE1, VoteTestData.VOTE2, VoteTestData.VOTE3));
        VOTE_MATCHER.assertMatch(votes, votesTest);
    }

}
