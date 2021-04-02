package ru.yakubov.vote.controller.user;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.controller.View;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.controller.RestUrlPattern.*;
import static ru.yakubov.vote.controller.SecurityUtil.authUserId;

/**
 * GET /rest/profiles                                                 get current user profile
 * GET /rest/profiles/vote                                            get current user vote
 * GET /rest/profiles/votes/in?date1=2021-03-08&date2=2021-03-10      get user vote by date (period)
 * PUT /rest/profiles                                                 update
 * POST /rest/profiles/vote?id={restaurantId}                         vote
 * POST /rest/rest/profiles/register                                  register new user
 **/

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractUserVoteController {

    public static final String REST_URL = ROOT_REST_URL + PROFILE_REST_URL;

    // /rest/profiles                                                 get current user profile
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVote get() {
        return super.get(authUserId());
    }

    // /rest/profiles/vote                                            get current user vote
    @GetMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo getCurrentVote() {
        return super.getCurrentVote(authUserId());
    }

    // /rest/profiles/votes/in?date1=2021-03-08&date2=2021-03-10      get user vote by date (period)
    @GetMapping(value = VOTES_URL + "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votes> getUserVoteByDate(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                         @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.getByUserDate(authUserId(), date1, date2);
    }

    // /rest/profiles                                                 update
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserVoteTo userVoteTo) {
        super.updateFromTo(userVoteTo, authUserId());
    }

    // /rest/profiles/vote?id={restaurantId}                         vote
    @PostMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVoteWithLocation(@RequestParam("id") int id) {
        VoteTo votesCreateTo = super.createVote(authUserId(), id);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreateTo);
    }

    // /rest/profiles/vote?id={restaurantId}                         vote
    @PutMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> updateVoteWithLocation(@RequestParam("id") int id) {
        VoteTo votesCreateTo = super.updateVote(authUserId(), id);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreateTo);
    }

    // /rest/rest/profiles/register                                  register new user
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createRegisterWithLocation(@Validated(View.Web.class) @RequestBody UserVoteTo userVoteTo) {
        UserVote created = super.createFromTo(userVoteTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}