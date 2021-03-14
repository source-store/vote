package ru.yakubov.vote.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.service.VoteService;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

public abstract class AbstractUserVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
//    @Lazy  проверить
    private VoteService voteService;

    @Autowired
    private UserVoteService service;

    public List<UserVote> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public UserVote get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public UserVote create(UserVote userVote) {
        log.info("create {}", userVote);
        checkNew(userVote);
        return service.create(userVote);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(UserVote userVote, int id) {
        log.info("update {} with id={}", userVote, id);
        assureIdConsistent(userVote, id);
        service.create(userVote);
    }

    public UserVote getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public Votes getUserVoteByOneDate(int id, LocalDate setDate){
        log.info("getUserVoteByOneDate userId {} date {}", id, setDate);
        return voteService.getByUserOneDate(id, setDate);
    }

    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate){
        log.info("getByUserDat userId {} beginDate {}  endDate {}", id, beginDate, endDate);
        return voteService.getByUserDate(id, beginDate, endDate);
    }
}
