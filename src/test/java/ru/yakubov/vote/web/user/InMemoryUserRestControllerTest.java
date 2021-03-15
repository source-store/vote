package ru.yakubov.vote.web.user;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.AbstractInmemoryTest;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.repository.inmemory.InMemoryUserRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNull;

public class InMemoryUserRestControllerTest extends AbstractInmemoryTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminVoteRestController controller;
    private static InMemoryUserRepository repository;

    @BeforeEach
    public void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminVoteRestController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @AfterEach
    public void afterClass() {
        appCtx.close();

    }

    @BeforeEach
    public void setup() {
        // re-initialize
        repository.init();
    }


    @Test
    public void delete() {
        controller.delete(UserTestData.USER_ID1);
        assertNull(repository.get(UserTestData.ADMIN_ID1));
    }





}
