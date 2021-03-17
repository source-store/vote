package ru.yakubov.vote.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.web.RestUrlPattern;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

public abstract class AbstractUserVoteController extends RestUrlPattern {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
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


    public UserVote createFromTo(UserVoteTo userVoteTo) {
        log.info("createFromTo {}", userVoteTo);
        UserVote userVote = VoteUtilsTo.createUserFromTo(userVoteTo);
        checkNew(userVote);
        return service.create(userVote);
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


    public void updateFromTo(UserVoteTo userVoteTo, int id) {
        log.info("updateFromTo {} with id={}", userVoteTo, id);
        UserVote userVote = VoteUtilsTo.createUserFromTo(userVoteTo);
        assureIdConsistent(userVote, id);
        service.create(userVote);
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

    public void vote(int userId, int restautantId) {
        log.info("vote userId {} restautantId {}", userId, restautantId);
         voteService.vote(userId, restautantId);
    }

    public void voteDelete(int userId) {
        log.info("vote userId {} ", userId);
        voteService.DeleteCurrentVote(userId);
    }

    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate){
        log.info("getByUserDat userId {} beginDate {}  endDate {}", id, beginDate, endDate);
        return voteService.getByUserDate(id, beginDate, endDate);
    }
}
