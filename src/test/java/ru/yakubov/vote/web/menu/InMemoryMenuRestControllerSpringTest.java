package ru.yakubov.vote.web.menu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.inmemory.InMemoryMenuRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.yakubov.vote.MenuTestData.*;

@ContextConfiguration({"classpath:spring/inmemory.xml"})
@RunWith(SpringRunner.class)
public class InMemoryMenuRestControllerSpringTest {

    @Autowired
    private MenuRestController controller;

    @Autowired
    private InMemoryMenuRepository repository;

    @Before
    public void setup() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(MenuTestData.MENU1.getId());
        Assert.assertNull(repository.get(MenuTestData.MENU1.getId()));
    }

    @Test
    public void getAll() {
        Assert.assertNotNull(controller.getAll());
    }

    @Test
    public void getAllByRestaurantId() {
        Set<Menu> all = new HashSet<Menu>(repository.getAllByRestaurantId(MENU1.getRestaurant().getId()));

        Set<Menu> menus = new HashSet<Menu>(List.of(MENU1, MENU2, MENU3, MENU6, MENU7));
        MENU_MATCHER.assertMatch(all, menus);
    }

    @Test
    public void getIdRestaurant() {
        Assert.assertEquals(repository.getIdRestaurant(MENU1.getId()), MENU1.getRestaurant().getId());
    }

}
