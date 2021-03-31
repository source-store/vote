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

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;
import static ru.yakubov.vote.web.RestUrlPattern.*;


/**
 * GET /rest/admin/profiles                                                   get all user profiles
 * GET /rest/admin/profiles/{id}                                              get user profile by id
 * GET /rest/profiles/in?email=user2@yandex.ru                                get profile by email
 * POST /rest/admin/profiles                                                  create new user from UserVote
 * DELETE /rest/admin/profiles/{userId}                                       delete user
 * PUT /rest/admin/profiles/{userId}                                          update user
 */

@RestController
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractUserVoteController {

    public static final String REST_URL = ROOT_REST_URL + ADMIN_REST_URL;

    // /rest/admin/profiles                                                   get all user profiles
    @Override
    @GetMapping(PROFILE_REST_URL)//тоже самое @RequestMapping(method = RequestMethod.GET)
    public List<UserVote> getAll() {
        SecurityUtil.safeGet();
        return super.getAll();
    }

    // /rest/admin/profiles/{id}                                              get user profile by id
    @Override
    @GetMapping(PROFILE_REST_URL + "/{id}")
    //в @PathVariable("id") "id" можно нее указывать, но пока делаю так.
    public UserVote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    // /rest/profiles/in?email=user2@yandex.ru                                get profile by email
    @GetMapping(PROFILE_REST_URL + "/in")
    public UserVote getProfileByMail(@RequestParam("email") String email) {
        return getByMail(email);
    }

    // /rest/admin/profiles                                                  create new user from UserVote
    @PostMapping(value = PROFILE_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserVote> createWithLocation(@Validated(View.Web.class) @RequestBody UserVote userVote) {
        UserVote userVoteCreate = create(userVote);

        URI uriOfNewUserVote = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + PROFILE_REST_URL + "/{id}").buildAndExpand(userVoteCreate.getId()).toUri();

        return ResponseEntity.created(uriOfNewUserVote).body(userVoteCreate);
    }

    // /rest/admin/profiles/{userId}                                       delete user
    @DeleteMapping(PROFILE_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable int id) {
        delete(id);
    }

    // /rest/admin/profiles/{userId}                                          update user
    @PutMapping(value = PROFILE_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@Validated(View.Web.class) @RequestBody UserVote userVote,
                              @PathVariable("id") int id) {
        update(userVote, id);
    }

    private void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    private void update(UserVote userVote, int id) {
        log.info("update {} with id={}", userVote, id);
        assureIdConsistent(userVote, id);
        service.create(userVote);
    }

    private UserVote create(UserVote userVote) {
        log.info("create {}", userVote);
        checkNew(userVote);
        return service.create(userVote);
    }

    private UserVote getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

}
