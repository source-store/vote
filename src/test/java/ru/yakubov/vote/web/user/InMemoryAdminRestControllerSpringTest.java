package ru.yakubov.vote.web.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.inmemory.InMemoryUserRepository;
import ru.yakubov.vote.util.exception.NotFoundException;

import static ru.yakubov.vote.UserTestData.USER_MATCHER;

@ContextConfiguration({"classpath:spring/inmemory.xml"})
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {
    @Autowired
    private AdminVoteRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setup() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(UserTestData.USER_ID1);
        Assert.assertNull(repository.get(UserTestData.USER_ID1));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> controller.delete(UserTestData.ADMIN_ID1));
    }

    @Test
    public void getByEmail(){
        UserVote userVote = repository.getByEmail("user1@yandex.ru");
        USER_MATCHER.assertMatch(userVote, UserTestData.user1);
    }

}
