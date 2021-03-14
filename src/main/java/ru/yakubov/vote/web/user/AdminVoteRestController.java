package ru.yakubov.vote.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = "/admin/profiles";
    public static final String VOTE_URL = "/vote";


    //http://localhost:8080/vote/admin/profiles
    @Override
    @GetMapping//однохуственно @RequestMapping(method = RequestMethod.GET)
    public List<UserVote> getAll() {
        return super.getAll();
    }

    //http://localhost:8080/vote/admin/profiles/50003
    @Override
    @GetMapping("/{id}")
    public UserVote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    //Пример json
    //{ "name": "User22", "email": "user22@yandex.ru", "password": "password", "enabled": true, "roles": ["USER"]}
    //принимаем объекты в теле в формате json
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserVote> createWithLocation(@RequestBody UserVote userVote) {
        UserVote userVoteCreate = super.create(userVote);
        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").buildAndExpand(userVoteCreate.getId()).toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(userVoteCreate);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }


    //Пример json
    //{ "name": "User222", "email": "user222@yandex.ru", "password": "password", "enabled": true, "registered": 1615732656452, "roles": ["USER"]}
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserVote userVote, @PathVariable("id") int id) {
        super.update(userVote, id);
    }



    //http://localhost:8080/vote/admin/profiles/in?email=user2@yandex.ru
    @Override
    @GetMapping("/in")
    public UserVote getByMail(@RequestParam("email") String email) {
        return super.getByMail(email);
    }

    //http://localhost:8080/vote/admin/profiles/50004/vote/by?date=2021-03-08
    @GetMapping("/{id}" + VOTE_URL + "/by")
    public Votes getUserVoteByOneDate(@PathVariable int id, @RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFormat = LocalDate.parse(date, formatter);
        return super.getUserVoteByOneDate(id, dateFormat);
    }

    //http://localhost:8080/vote/admin/profiles/50004/vote/in?date1=2021-03-08&date2=2021-03-10
    @GetMapping("/{id}" + VOTE_URL + "/in")
    public List<Votes> getUserVoteByDate(@PathVariable int id, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(date1, formatter);
        LocalDate endDate = LocalDate.parse(date2, formatter);
        return super.getByUserDate(id, beginDate, endDate);
    }
}
