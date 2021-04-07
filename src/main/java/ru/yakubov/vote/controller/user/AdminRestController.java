package ru.yakubov.vote.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.controller.SecurityUtil;
import ru.yakubov.vote.controller.View;
import ru.yakubov.vote.model.User;

import java.net.URI;
import java.util.List;

import static ru.yakubov.vote.controller.RestUrlPattern.*;
import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;


/**
 * GET /rest/admin/profiles                                                   get all user profiles
 * GET /rest/admin/profiles/{id}                                              get user profile by id
 * GET /rest/profiles/in?email=user2@yandex.ru                                get profile by email
 * POST /rest/admin/profiles                                                  create new user from User
 * DELETE /rest/admin/profiles/{userId}                                       delete user
 * PUT /rest/admin/profiles/{userId}                                          update user
 */

@RestController
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController extends AbstractUserController {

    public static final String REST_URL = ROOT_REST_URL + ADMIN_REST_URL;

    // /rest/admin/profiles                                                   get all user profiles
    @Override
    @GetMapping(PROFILES_REST_URL)//тоже самое @RequestMapping(method = RequestMethod.GET)
    public List<User> getAll() {
        SecurityUtil.safeGet();
        return super.getAll();
    }

    // /rest/admin/profiles/{id}                                              get user profile by id
    @Override
    @GetMapping(PROFILES_REST_URL + "/{id}")
    //в @PathVariable("id") "id" можно нее указывать, но пока делаю так.
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    // /rest/profiles/in?email=user2@yandex.ru                                get profile by email
    @GetMapping(PROFILES_REST_URL + "/in")
    public User getProfileByMail(@RequestParam("email") String email) {
        return getByMail(email);
    }

    // /rest/admin/profiles                                                  create new user from User
    @PostMapping(value = PROFILES_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> createWithLocation(@Validated(View.Web.class) @RequestBody User user) {
        User userCreate = create(user);

        URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + PROFILES_REST_URL + "/{id}").buildAndExpand(userCreate.getId()).toUri();

        return ResponseEntity.created(uriOfNewUser).body(userCreate);
    }

    // /rest/admin/profiles/{userId}                                       delete user
    @DeleteMapping(PROFILES_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable int id) {
        delete(id);
    }

    // /rest/admin/profiles/{userId}                                          update user
    @PutMapping(value = PROFILES_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@Validated(View.Web.class) @RequestBody User user,
                              @PathVariable("id") int id) {
        update(user, id);
    }

    private void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    private void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.create(user);
    }

    private User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    private User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

}
