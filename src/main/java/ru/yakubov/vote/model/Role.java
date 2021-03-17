package ru.yakubov.vote.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
//        return name();
        return "ROLE_"+name();
    }
}