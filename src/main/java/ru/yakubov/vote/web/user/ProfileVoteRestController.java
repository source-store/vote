package ru.yakubov.vote.web.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.to.BaseTo;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.to.UserVoteTo;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.web.View;

import javax.validation.Valid;

import java.net.URI;

import static ru.yakubov.vote.web.SecurityUtil.authUserId;

/*
*       GET /profile                    get current user profile
*       POST /profile/register          register new User from UserVoteTo (Role.USER)
*       PUT /profile                    update
*       POST /profile/{restaurantId}    vote
*       DELETE /profile                 delete current user vote
**/

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = ROOT_REST_URL+PROFILE_REST_URL;

    //{"name": "User22", "email": "user25@yandex.ru", "password": "password"}
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> register(@Validated(View.Web.class) @RequestBody UserVoteTo userVoteTo) {
        UserVote created = super.createFromTo(userVoteTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").build().toUri();
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

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createWithLocation(@PathVariable int id) {
        VoteTo votesCreate = super.createVote(authUserId(), id);
        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").buildAndExpand(votesCreate.getId()).toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(votesCreate);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void voteDelete() {
        super.voteDelete(authUserId());
    }

}