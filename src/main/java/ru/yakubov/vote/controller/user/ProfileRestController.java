package ru.yakubov.vote.controller.user;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.controller.View;
import ru.yakubov.vote.model.User;
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
 * GET /rest/profile/votes/in?beginDate=2021-03-08&endDate=2021-03-10      get user vote by date (period)
 * PUT /rest/profile                                                 update
 * POST /rest/profile/vote?id={restaurantId}                         vote
 * PUT /rest/profile/vote?id={restaurantId}                          update current vote
 * POST /rest/rest/profile/register                                  register new user
 **/

@RestController
@RequestMapping(value = ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {

    public static final String REST_URL = ROOT_REST_URL + PROFILE_REST_URL;

    // /rest/profile                                                 get current user profile
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(authUserId());
    }

    // /rest/profile/vote                                            get current user vote
    @GetMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo getTodayVote() {
        return super.getTodayVote(authUserId());
    }

    // /rest/profile/votes/in?beginDate=2021-03-08&endDate=2021-03-10      get user vote by date (period)
    @GetMapping(value = VOTES_URL + "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getUserVoteByPeriod(@RequestParam("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return super.getUserVoteByPeriod(authUserId(), beginDate, endDate);
    }

    // /rest/profile                                                 update
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        super.updateFromTo(userTo, authUserId());
    }

    // /rest/rest/profile/register                                  register new user
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> createRegisterWithLocation(@Validated(View.Web.class) @RequestBody UserTo userTo) {
        User created = super.createFromTo(userTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}