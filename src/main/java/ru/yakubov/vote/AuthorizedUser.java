package ru.yakubov.vote;

import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;

import static ru.yakubov.vote.web.SecurityUtil.safeGet;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{
    private static final long serialVersionUID = 7496650878122997852L;

    private UserVoteTo userVoteTo;

    public AuthorizedUser(UserVote userVote) {
        super(userVote.getEmail(), userVote.getPassword(), userVote.isEnabled(), true, true, true, userVote.getRoles());
        this.userVoteTo = VoteUtilsTo.createTo(userVote);
    }
    public UserVoteTo getUser() {
        return this.userVoteTo;
    }

    public int getId() {
        return this.userVoteTo.id();
    }

    public void update(UserVoteTo newUserVoteTo) {
        this.userVoteTo = newUserVoteTo;
    }

    @Override
    public String toString() {
        return userVoteTo.toString();
    }
}
