package ru.yakubov.vote.web.user;

import org.springframework.stereotype.Controller;
import ru.yakubov.vote.model.UserVote;

import java.util.List;

@Controller
public class AdminVoteRestController extends AbstractUserVoteController{

    @Override
    public List<UserVote> getAll() {
        return super.getAll();
    }

    @Override
    public UserVote get(int id) {
        return super.get(id);
    }

    @Override
    public UserVote create(UserVote userVote) {
        return super.create(userVote);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(UserVote userVote, int id) {
        super.update(userVote, id);
    }

    @Override
    public UserVote getByMail(String email) {
        return super.getByMail(email);
    }
}
