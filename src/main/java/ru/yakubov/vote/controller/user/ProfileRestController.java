package ru.yakubov.vote.controller.user;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.controller.View;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.to.UserTo;
import ru.yakubov.vote.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.controller.RestUrlPattern.*;
import static ru.yakubov.vote.controller.SecurityUtil.authUserId;

/**
 * GET /rest/profile                                                 get current user profile
 * GET /rest/profile/vote                                            get current user vote
 * GET /rest/profile/votes/in?date1=2021-03-08&date2=2021-03-10      get user vote by date (period)
 * PUT /rest/profile                                                 update
 * POST /rest/profile/vote?id={restaurantId}                         vote
 * POST /rest/rest/profile/register                                  register new user
 **/

@RestController
@RequestMapping(value = ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {

    public static final String REST_URL = ROOT_REST_URL + PROFILE_REST_URL;

    // /rest/profile                                                 get current user profile
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVote get() {
        return super.get(authUserId());
    }

    // /rest/profile/vote                                            get current user vote
    @GetMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo getCurrentVote() {
        return super.getCurrentVote(authUserId());
    }

    // /rest/profile/votes/in?date1=2021-03-08&date2=2021-03-10      get user vote by date (period)
    @GetMapping(value = VOTES_URL + "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Votes> getUserVoteByDate(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                         @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.getByUserDate(authUserId(), date1, date2);
    }

    // /rest/profile                                                 update
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        super.updateFromTo(userTo, authUserId());
    }

    // /rest/profile/vote?id={restaurantId}                         vote
    @PostMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVoteWithLocation(@RequestParam("id") int id) {
        VoteTo votesCreateTo = super.createVote(authUserId(), id);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreateTo);
    }

    // /rest/profile/vote?id={restaurantId}                         vote
    @PutMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> updateVoteWithLocation(@RequestParam("id") int id) {
        VoteTo votesCreateTo = super.updateVote(authUserId(), id);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + VOTE_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreateTo);
    }

    // /rest/rest/profile/register                                  register new user
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createRegisterWithLocation(@Validated(View.Web.class) @RequestBody UserTo userTo) {
        UserVote created = super.createFromTo(userTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}