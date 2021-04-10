package ru.yakubov.vote.controller.vote;

/*
 * @autor Alexandr.Yakubov
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.controller.RestUrlPattern;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.service.VoteService;
import ru.yakubov.vote.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.controller.RestUrlPattern.VOTE_URL;
import static ru.yakubov.vote.controller.SecurityUtil.authUserId;

/**
 * GET /rest/votes                                         get result vote current date
 * GET /rest/votes/in?beginDate=YYYY-MM-DD&endDate=YYYY-MM-DD    get result vote by period
 **/

@RestController
@RequestMapping(value = VoteRestController.REST_URL)
public class VoteRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String REST_URL = RestUrlPattern.ROOT_REST_URL + RestUrlPattern.VOTES_URL;

    @Autowired
    protected VoteService voteService;

    // /rest/votes                                         get result vote current date
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultToday() {
        log.info("getResultToday ");
        return voteService.getResultDate(LocalDate.now(), LocalDate.now());
    }

    // /rest/votes/in?beginDate=YYYY-MM-DD&endDate=YYYY-MM-DD    get result vote by period
    @GetMapping(value = "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultDatePeriod(@RequestParam("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("getResultDatePeriod beginDate {}  endDate {}", beginDate, endDate);
        return voteService.getResultDate(beginDate, endDate);
    }

    // /rest/votes?id={restaurantId}                         vote
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVoteWithLocation(@RequestParam("restaurantid") int restaurantId) {
        VoteTo votesCreateTo = createVote(authUserId(), restaurantId);

        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUser).body(votesCreateTo);
    }

    // /rest/votes?id={restaurantId}                         vote
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> updateVoteWithLocation(@RequestParam("restaurantid") int restaurantId) {
        VoteTo votesCreateTo = updateVote(authUserId(), restaurantId);

        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUser).body(votesCreateTo);
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
