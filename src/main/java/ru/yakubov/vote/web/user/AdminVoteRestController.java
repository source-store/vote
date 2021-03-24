package ru.yakubov.vote.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.web.SecurityUtil;
import ru.yakubov.vote.web.View;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.yakubov.vote.util.DateTimeUtil.makeDateFromString;


/*
* GET /admin/result                                                     get result vote current date
* GET /admin/result/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD                get result vote by period
* GET /admin/profile                                                    get all user profiles
* GET /admin/profile/{userId}                                           get user profile by id
* POST /admin/profile                                                   create new user from UserVote
* DELETE /admin/profile/{userId}                                        delete user
* PUT /admin/profile/{userId}                                           update user
* GET /profiles/in?email=user2@yandex.ru                                get profile by email
* GET /profiles/{userId}}/vote/in?date1=2021-03-08&date2=2021-03-10     get user vote by date (period)
* */

@RestController
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractUserVoteController{

    public static final String REST_URL = ROOT_REST_URL+ADMIN_REST_URL+PROFILE_REST_URL;
    public static final String VOTE_URL = "/vote";

    @GetMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultCurdate() {
        return super.getResultCurdate();
    }

    @GetMapping(value = "/result/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultDatePeriod(@RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        return super.getResultDatePeriod(makeDateFromString(date1), makeDateFromString(date2));
    }

    //http://localhost:8080/vote/admin/profiles
    @Override
    @GetMapping//тоже самое @RequestMapping(method = RequestMethod.GET)
    public List<UserVote> getAll() {
        SecurityUtil.safeGet();
        return super.getAll();
    }

    //http://localhost:8080/vote/admin/profile/50003
    @Override
    @GetMapping("/{id}")
    //в @PathVariable("id") "id" можно нее указывать, но пока делаю так.
    public UserVote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    //Пример json
    //{ "name": "User22", "email": "user22@yandex.ru", "password": "password", "enabled": true, "roles": ["USER"]}
    //принимаем объекты в теле в формате json
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createWithLocation(@Validated(View.Web.class) @RequestBody UserVote userVote) {
        UserVote userVoteCreate = super.create(userVote);
        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").buildAndExpand(userVoteCreate.getId()).toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(userVoteCreate);
    }

    //http://localhost:8080/vote/admin/profile/50003
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    //http://localhost:8080/vote/admin/profile/50003
    //Пример json
    //{ "name": "User222", "email": "user222@yandex.ru", "password": "password", "enabled": true, "registered": 1615732656452, "roles": ["USER"]}
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody UserVote userVote, @PathVariable("id") int id) {
        super.update(userVote, id);
    }

    //http://localhost:8080/vote/admin/profiles/in?email=user2@yandex.ru
    @Override
    @GetMapping("/in")
    public UserVote getByMail(@RequestParam("email") String email) {
        return super.getByMail(email);
    }

    //http://localhost:8080/vote/admin/profile/50004/vote/in?date1=2021-03-08&date2=2021-03-10
    @GetMapping("/{id}" + VOTE_URL + "/in")
    public List<Votes> getUserVoteByDate(@PathVariable int id, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        return super.getByUserDate(id, makeDateFromString(date1), makeDateFromString(date2));
    }

    private LocalDate makeDateFromString(String dateTemplate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateTemplate, formatter);
    }
}
