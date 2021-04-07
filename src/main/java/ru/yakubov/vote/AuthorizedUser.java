package ru.yakubov.vote;

import ru.yakubov.vote.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 7496650878122997852L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public int getId() {
        return this.user.id();
    }

    public void update(User newUser) {
        this.user = newUser;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
