package ru.yakubov.vote.web.user;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.repository.inmemory.InMemoryUserRepository;

import java.util.Arrays;

public class InMemoryUserRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminVoteRestController controller;
    private static InMemoryUserRepository repository;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminVoteRestController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();

    }

    @Before
    public void setup() {
        // re-initialize
        repository.init();
    }


    @Test
    public void delete() {
        controller.delete(UserTestData.USER_ID1);
        Assert.assertNull(repository.get(UserTestData.ADMIN_ID1));
    }





}
