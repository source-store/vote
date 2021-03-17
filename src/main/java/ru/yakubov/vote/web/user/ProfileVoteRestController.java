package ru.yakubov.vote.web.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.to.UserVoteTo;

import javax.validation.Valid;

import java.net.URI;

import static ru.yakubov.vote.web.SecurityUtil.authUserId;

/*
*       GET /profile                get current user profile
*       POST /profile/register      register new User from UserVoteTo (Role.USER)
*       PUT /profile                update
*       DELETE /profile             delete current user vote
**/

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = ROOT_REST_URL+PROFILE_REST_URL;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> register(@Valid @RequestBody UserVoteTo userTo) {
        UserVote created = super.createFromTo(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
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

    @GetMapping("/vote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable("id") int id) {
        super.vote(authUserId(), id);
    }


    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void voteDelete() {
        super.voteDelete(authUserId());
    }

}
