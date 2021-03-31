package ru.yakubov.vote.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.service.UserVoteService;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

public abstract class AbstractUserVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected UserVoteService service;

    protected List<UserVote> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    protected UserVote get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    protected void updateFromTo(UserVoteTo userVoteTo, int id) {
        log.info("updateFromTo {} with id={}", userVoteTo, id);
        UserVote userVote = VoteUtilsTo.createUserFromTo(userVoteTo);
        assureIdConsistent(userVote, id);
        service.create(userVote);
    }

    protected List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        log.info("getByUserDat userId {} beginDate {}  endDate {}", id, beginDate, endDate);
        return voteService.getByUserDate(id, beginDate, endDate);
    }

    protected VoteTo getCurrentVote(int id) {
        log.info("getCurrentVote userId {} Date {}", id, LocalDate.now());
        return VoteUtilsTo.createTo(voteService.getByUserOneDate(id, LocalDate.now()));
    }

    protected UserVote createFromTo(UserVoteTo userVoteTo) {
        log.info("createFromTo userVoteTo {}", userVoteTo);
        UserVote userVote = VoteUtilsTo.createUserFromTo(userVoteTo);
        checkNew(userVote);
        return service.create(userVote);
    }

    protected VoteTo createVote(int userId, int restautantId) {
        log.info("createVote userId {} restautantId {}", userId, restautantId);
        return voteService.vote(userId, restautantId);
    }

    protected VoteTo updateVote(int userId, int restautantId) {
        log.info("updateVote userId {} restautantId {}", userId, restautantId);
        return voteService.updateVote(userId, restautantId);
    }
}
