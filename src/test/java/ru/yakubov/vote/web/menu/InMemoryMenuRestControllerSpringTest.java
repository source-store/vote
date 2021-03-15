package ru.yakubov.vote.web.menu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.AbstractInmemoryTest;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.inmemory.InMemoryMenuRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yakubov.vote.MenuTestData.*;

public class InMemoryMenuRestControllerSpringTest extends AbstractInmemoryTest {

    @Autowired
    private MenuRestController controller;

    @Autowired
    private InMemoryMenuRepository repository;

    @BeforeEach
    public void setup() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(MenuTestData.MENU1.getId());
        assertNull(repository.get(MenuTestData.MENU1.getId()));
    }

    @Test
    public void getAll() {
        assertNotNull(controller.getAll());
    }

    @Test
    public void getAllByRestaurantId() {
        Set<Menu> all = new HashSet<Menu>(repository.getAllByRestaurantId(MENU1.getRestaurant().getId()));

        Set<Menu> menus = new HashSet<Menu>(List.of(MENU1, MENU2, MENU3, MENU6, MENU7));
        MENU_MATCHER.assertMatch(all, menus);
    }

    @Test
    public void getIdRestaurant() {
        assertEquals(repository.getIdRestaurant(MENU1.getId()), MENU1.getRestaurant().getId());
    }

}
