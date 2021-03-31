package ru.yakubov.vote;

import ru.yakubov.vote.model.Votes;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.MenuTestData.MENU_END_SEQ;

public class VoteTestData {
    public static TestMatcher<Votes> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator("date", "restaurant", "userVote");

    public static final int VOTE_TEST_SEQ = MENU_END_SEQ;

    public static final int VOTE_ID1 = VOTE_TEST_SEQ + 1;
    public static final int VOTE_ID2 = VOTE_TEST_SEQ + 2;
    public static final int VOTE_ID3 = VOTE_TEST_SEQ + 3;
    public static final int VOTE_ID4 = VOTE_TEST_SEQ + 4;

    public static final Votes NEW_VOTE = new Votes(null, LocalDate.of(2021, 3, 20));

    public static final Votes VOTE1 = new Votes(VOTE_ID1, LocalDate.of(2021, 3, 8));
    public static final Votes VOTE2 = new Votes(VOTE_ID2, LocalDate.of(2021, 3, 8));
    public static final Votes VOTE3 = new Votes(VOTE_ID3, LocalDate.of(2021, 3, 8));
    public static final Votes VOTE4 = new Votes(VOTE_ID4, LocalDate.of(2021, 3, 10));

    public static final List<Votes> VOTES1;
    public static final List<Votes> VOTES2;

    public static final List<Votes> VOTES_FOR_USER1;
    public static final List<Votes> VOTES_FOR_USER2;
    public static final List<Votes> VOTES_FOR_USER3;

    static {
        VOTE1.setUserVote(UserTestData.user1);
        VOTE2.setUserVote(UserTestData.user2);
        VOTE3.setUserVote(UserTestData.user3);
        VOTE4.setUserVote(UserTestData.user3);

        VOTE1.setRestaurant(RestaurantTestData.restaurant1);
        VOTE2.setRestaurant(RestaurantTestData.restaurant1);
        VOTE3.setRestaurant(RestaurantTestData.restaurant2);
        VOTE4.setRestaurant(RestaurantTestData.restaurant2);

        VOTES1 = List.of(VOTE1, VOTE2);
        VOTES2 = List.of(VOTE3, VOTE4);
        VOTES_FOR_USER1 = List.of(VOTE1);
        VOTES_FOR_USER2 = List.of(VOTE2);
        VOTES_FOR_USER3 = List.of(VOTE3, VOTE4);
    }

}