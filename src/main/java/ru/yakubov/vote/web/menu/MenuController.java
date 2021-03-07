package ru.yakubov.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.to.MenuTo;
import ru.yakubov.vote.util.VoteUtils;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;


@Controller
public class MenuController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    public List<MenuTo> getAll() {
        log.info("getAll");
        return VoteUtils.getMenuTos(service.getAll());
    }

    public List<MenuTo> getAllByRestaurantId(int restaurantId) {
        return  VoteUtils.getMenuTos(service.getAllByRestaurantId(restaurantId));
    }

    public Menu get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Menu create(Menu menu) {
        log.info("create {}", menu);
        checkNew(menu);
        return service.create(menu);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Menu menu, int id) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        service.update(menu);
    }

    public Integer getIdRestaurant(int id) {
        log.info("getIdRestaurant {} with id={}", id);
        return service.getIdRestaurant(id);
    }

}
