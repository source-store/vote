package ru.yakubov.vote.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.User;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.service.UserService;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.UserTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected UserService service;

    protected List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    protected User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    protected void updateFromTo(UserTo userTo, int id) {
        log.info("updateFromTo {} with id={}", userTo, id);
        User user = VoteUtilsTo.createUserFromTo(userTo);
        assureIdConsistent(user, id);
        service.create(user);
    }

    protected List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        log.info("getByUserDat userId {} beginDate {}  endDate {}", id, beginDate, endDate);
        return voteService.getByUserDate(id, beginDate, endDate);
    }

    protected VoteTo getCurrentVote(int id) {
        log.info("getCurrentVote userId {} Date {}", id, LocalDate.now());
        Votes votes = voteService.getByUserOneDate(id, LocalDate.now());
        Assert.notNull(votes, "User not vote today");
        return VoteUtilsTo.createTo(votes);
    }

    protected User createFromTo(UserTo userTo) {
        log.info("createFromTo userTo {}", userTo);
        User user = VoteUtilsTo.createUserFromTo(userTo);
        checkNew(user);
        return service.create(user);
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
