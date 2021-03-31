package ru.yakubov.vote;

import lombok.Getter;
import ru.yakubov.vote.model.UserVote;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 7496650878122997852L;

    private UserVote userVote;

    public AuthorizedUser(UserVote userVote) {
        super(userVote.getEmail(), userVote.getPassword(), userVote.isEnabled(), true, true, true, userVote.getRoles());
        this.userVote = userVote;
    }

    public UserVote getUser() {
        return this.userVote;
    }

    public int getId() {
        return this.userVote.id();
    }

    public void update(UserVote newUserVote) {
        this.userVote = newUserVote;
    }

    @Override
    public String toString() {
        return userVote.toString();
    }
}
