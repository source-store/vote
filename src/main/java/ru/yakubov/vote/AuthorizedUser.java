package ru.yakubov.vote;

import ru.yakubov.vote.model.UserVote;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{

    private UserVote user;


    public AuthorizedUser(UserVote userVote) {
//        super(userVote.getEmail(), userVote.getPassword(), userVote.isEnabled(), true, true, userVote.isEnabled(), userVote.getRoles());
        super(userVote.getEmail(), userVote.getPassword(), userVote.getRoles());
//        this.userTo = VoteUtilsTo.createTo(userVote);
        this.user = userVote;
    }

    public int getId() {
        return this.user.getId();
    }

    public void update(UserVote user) {
        this.user = user;
    }

    public UserVote getUser() {
        return this.user;
    }

    @Override
    public String toString() {
        return this.user.toString();
    }

}
