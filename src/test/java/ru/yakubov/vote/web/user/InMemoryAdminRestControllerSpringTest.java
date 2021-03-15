package ru.yakubov.vote.web.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.AbstractInmemoryTest;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.inmemory.InMemoryUserRepository;
import ru.yakubov.vote.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yakubov.vote.UserTestData.USER_MATCHER;

public class InMemoryAdminRestControllerSpringTest  extends AbstractInmemoryTest {
    @Autowired
    private AdminVoteRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    public void setup() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(UserTestData.USER_ID1);
        assertNull(repository.get(UserTestData.USER_ID1));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(UserTestData.ADMIN_ID1));
    }

    @Test
    public void getByEmail(){
        UserVote userVote = repository.getByEmail("user1@yandex.ru");
        USER_MATCHER.assertMatch(userVote, UserTestData.user1);
    }

}
