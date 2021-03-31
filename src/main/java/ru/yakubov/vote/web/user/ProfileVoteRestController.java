package ru.yakubov.vote.web.user;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.web.View;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.web.SecurityUtil.authUserId;

/*
 *       GET /profiles/results                                         get result vote current date
 *       GET /profiles/results/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
 *       GET /profiles                                                 get current user profile
 *       GET /profiles/vote                                            get current user vote
 *       GET /profiles/votes/in?date1=2021-03-08&date2=2021-03-10      get user vote by date (period)
 *       PUT /profiles                                                 update
 *       POST /profiles/vote?id={restaurantId}                         vote
 *       POST /rest/profiles/register                                  register new user
 **/

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractUserVoteController {

    public static final String REST_URL = ROOT_REST_URL + PROFILE_REST_URL;

    //GET /profiles/results                                         get result vote current date
    @GetMapping(value = RESULT_VOTE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultCurdate() {
        return super.getResultCurdate();
    }

    //GET /profiles/results/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
    @GetMapping(value = RESULT_VOTE_REST_URL + "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultDatePeriod(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.getResultDatePeriod(date1, date2);
    }

    //GET /profiles                                                 get current user profile
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVote get() {
        return super.get(authUserId());
    }

    //GET /profiles/vote                                            get current user vote
    @GetMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo getCurrentVote() {
        return super.getCurrentVote(authUserId());
    }

    //http://localhost:8080/vote/profiles/votes/in?date1=2021-03-08&date2=2021-03-10
    @GetMapping(value = VOTES_URL+"/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votes> getUserVoteByDate(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                         @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.getByUserDate(authUserId(), date1, date2);
    }


    //PUT /profiles                                                 update
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserVoteTo userVoteTo) {
        super.updateFromTo(userVoteTo, authUserId());
    }

    //POST /profiles/vote?id={restaurantId}                         vote
    @PostMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVoteWithLocation(@RequestParam("id") int id) {
        VoteTo votesCreateTo = super.createVote(authUserId(), id);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreateTo);
    }

    //POST /rest/profiles/register                                  register new user
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createRegisterWithLocation(@Validated(View.Web.class) @RequestBody UserVoteTo userVoteTo) {
        UserVote created = super.createFromTo(userVoteTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}