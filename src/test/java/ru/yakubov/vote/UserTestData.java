package ru.yakubov.vote;

import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.UserVote;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {
    public static TestMatcher<UserVote> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator("votes", "registered", "password");

    public static final int TEST_START_SEQ = 50_000;

    public static final int ADMIN_ID1 = TEST_START_SEQ;
    public static final int ADMIN_ID2 = TEST_START_SEQ + 1;
    public static final int USER_ID1 = TEST_START_SEQ + 2;
    public static final int USER_ID2 = TEST_START_SEQ + 3;
    public static final int USER_ID3 = TEST_START_SEQ + 4;

    public static final UserVote newUser = new UserVote(null, "newUser", "newuser@yandex.ru", "password", Role.USER);
    public static final UserVote admin1 = new UserVote(ADMIN_ID1, "Admin1", "admin1@yandex.ru", "password1", Role.ADMIN);
    public static final UserVote admin2 = new UserVote(ADMIN_ID2, "Admin2", "admin2@yandex.ru", "password2", Role.ADMIN);
    public static final UserVote user1 = new UserVote(USER_ID1, "User1", "user1@yandex.ru", "password3", Role.USER);
    public static final UserVote user2 = new UserVote(USER_ID2, "User2", "user2@yandex.ru", "password4", Role.USER, Role.ADMIN);
    public static final UserVote user3 = new UserVote(USER_ID3, "User3", "user3@yandex.ru", "password5", Role.USER);

    public static final List<UserVote> users = new ArrayList<>();

    static {
        user1.setVotes(VoteTestData.VOTES_FOR_USER1);
        user2.setVotes(VoteTestData.VOTES_FOR_USER2);
        user3.setVotes(VoteTestData.VOTES_FOR_USER3);
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }


}
