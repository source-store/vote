package ru.yakubov.vote.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.yakubov.vote.AbstractTest;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;

@ActiveProfiles("datajpa")
public class UserVoteServiceTest extends AbstractTest {

    @Autowired
    protected UserVoteService service;

    @Test
    public void getAll() {
        assertNotNull(service.getAll());
    }

    @Test
    public void getByRoles() {
        assertNotNull(service.getByRoles(Role.USER));
    }


    @Test
    public void get() {
        USER_MATCHER.assertMatch(service.get(UserTestData.USER_ID1), UserTestData.user1);
    }

    @Test
    public void create() {
        UserVote created = service.create(UserTestData.newUser);
        int id = created.getId();
        UserTestData.newUser.setId(id);
        USER_MATCHER.assertMatch(service.get(id),UserTestData.newUser);
    }

    @Test
    public void save() {
        UserVote updateUser = service.get(UserTestData.USER_ID1);
        int id = updateUser.getId();
        updateUser.setEmail("update@mail.ru");
        service.create(updateUser);
        assertEquals(service.get(id).getEmail(), "update@mail.ru");
    }

    @Test
    public void delete() {
        assertNotNull(service.get(UserTestData.USER_ID1));
        service.delete(UserTestData.USER_ID1);
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.USER_ID1));
    }

}