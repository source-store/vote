package ru.yakubov.vote.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;

import java.net.URI;
import java.util.List;

@RestController
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = "/admin/profiles";

    @Override
    @GetMapping//однохуственно @RequestMapping(method = RequestMethod.GET)
    public List<UserVote> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public UserVote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    //принимаем объекты в теле в формате json
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> createWithLocation(@RequestBody UserVote userVote) {
        UserVote userVoteCreate = super.create(userVote);
        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}")
                .buildAndExpand(userVoteCreate.getId()).toUri();
        return ResponseEntity.created(uriOfNewUserVote).body(userVoteCreate);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserVote userVote, @PathVariable("id") int id) {
        super.update(userVote, id);
    }

    @Override
    @GetMapping("/by")
    public UserVote getByMail(@RequestParam("email") String email) {
        return super.getByMail(email);
    }
}
