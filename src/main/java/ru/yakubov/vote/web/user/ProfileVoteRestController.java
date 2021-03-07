package ru.yakubov.vote.web.user;


import org.springframework.stereotype.Controller;
import ru.yakubov.vote.model.UserVote;

import static ru.yakubov.vote.web.SecurityUtil.authUserId;

@Controller
public class ProfileVoteRestController extends AbstractUserVoteController{
    public UserVote get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(UserVote userVote) {
        super.update(userVote, authUserId());
    }
}
