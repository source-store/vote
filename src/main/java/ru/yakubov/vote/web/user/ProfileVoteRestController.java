package ru.yakubov.vote.web.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;

import java.net.URI;

import static ru.yakubov.vote.web.SecurityUtil.authUserId;

/*
*       GET /profile                    get current user profile
*       PUT /profile                    update
*       POST /profile/{restaurantId}    vote
*       DELETE /profile                 delete current user vote
**/

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = ROOT_REST_URL+PROFILE_REST_URL;

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

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void voteDelete() {
        super.voteDelete(authUserId());
    }

}