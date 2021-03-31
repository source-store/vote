package ru.yakubov.vote.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.web.SecurityUtil;
import ru.yakubov.vote.web.View;

import java.net.URI;
import java.util.List;

import static ru.yakubov.vote.web.RestUrlPattern.*;


/*
 * GET /admin/profiles                                                   get all user profiles
 * GET /admin/profiles/{id}                                              get user profile by id
 * GET /profiles/in?email=user2@yandex.ru                                get profile by email
 * POST /admin/profiles                                                  create new user from UserVote
 * DELETE /admin/profiles/{userId}                                       delete user
 * PUT /admin/profiles/{userId}                                          update user
 * */

@RestController
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractUserVoteController {

    public static final String REST_URL = ROOT_REST_URL + ADMIN_REST_URL;

    //http://localhost:8080/vote/admin/profiles
    @Override
    @GetMapping(PROFILE_REST_URL)//тоже самое @RequestMapping(method = RequestMethod.GET)
    public List<UserVote> getAll() {
        SecurityUtil.safeGet();
        return super.getAll();
    }

    //http://localhost:8080/vote/admin/profiles/50003
    @Override
    @GetMapping(PROFILE_REST_URL + "/{id}")
    //в @PathVariable("id") "id" можно нее указывать, но пока делаю так.
    public UserVote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    //http://localhost:8080/vote/admin/profiles/in?email=user2@yandex.ru
    @Override
    @GetMapping(PROFILE_REST_URL + "/in")
    public UserVote getByMail(@RequestParam("email") String email) {
        return super.getByMail(email);
    }

    //Пример json
    //{ "name": "User22", "email": "user22@yandex.ru", "password": "password", "enabled": true, "roles": ["USER"]}
    //принимаем объекты в теле в формате json
    @PostMapping(value = PROFILE_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createWithLocation(@Validated(View.Web.class) @RequestBody UserVote userVote) {
        UserVote userVoteCreate = super.create(userVote);
        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+PROFILE_REST_URL+ "/{id}").buildAndExpand(userVoteCreate.getId()).toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(userVoteCreate);
    }

    //http://localhost:8080/vote/admin/profiles/50003
    @Override
    @DeleteMapping(PROFILE_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    //http://localhost:8080/vote/admin/profiles/50003
    //Пример json
    //{ "name": "User222", "email": "user222@yandex.ru", "password": "password", "enabled": true, "registered": 1615732656452, "roles": ["USER"]}
    @Override
    @PutMapping(value = PROFILE_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody UserVote userVote, @PathVariable("id") int id) {
        super.update(userVote, id);
    }

}
