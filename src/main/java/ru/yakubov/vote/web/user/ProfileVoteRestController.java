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
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.web.View;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.web.SecurityUtil.authUserId;

/*
*       GET /result                                         get result vote current date
*       GET /result/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
*       GET /profile                                        get current user profile
*       PUT /profile                                        update
*       POST /profile/{restaurantId}                        vote
*       POST /rest/profile/register                         register new user
*       DELETE /profile                                     delete current user vote
**/

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = ROOT_REST_URL+PROFILE_REST_URL;

    @GetMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultCurdate() {
        return super.getResultCurdate();
    }

    @GetMapping(value = "/result/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultDatePeriod(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.getResultDatePeriod(date1, date2);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVote get() {
        return super.get(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserVoteTo userVoteTo) {
        super.updateFromTo(userVoteTo, authUserId());
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVoteWithLocation(@PathVariable int id) {
        VoteTo votesCreateTo = super.createVote(authUserId(), id);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").buildAndExpand(votesCreateTo.getId()).toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreateTo);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createRegisterWithLocation(@Validated(View.Web.class) @RequestBody UserVoteTo userVoteTo) {
        UserVote created = super.createFromTo(userVoteTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void voteDelete() {
        super.voteDelete(authUserId());
    }

}