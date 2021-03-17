package ru.yakubov.vote.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.yakubov.vote.model.UserVote;

public class UserUtil {
    public static UserVote prepareToSave(UserVote userVote, PasswordEncoder passwordEncoder) {
        String password = userVote.getPassword();
        userVote.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        userVote.setEmail(userVote.getEmail().toLowerCase());
        return userVote;
    }
}
