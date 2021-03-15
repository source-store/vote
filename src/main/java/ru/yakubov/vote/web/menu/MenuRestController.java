package ru.yakubov.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.to.MenuTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.web.SecurityUtil;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;


//@Controller
//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    public static final String REST_URL = "/menu";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final MenuService service;

    public MenuRestController(MenuService service) {
        this.service = service;
    }

    public List<Menu> getAll() {
        log.info("getAll");
        return service.getAll();
    }

//    @GetMapping
    public List<MenuTo> getAllTo() {
        log.info("getAllTo");
        return VoteUtilsTo.getMenuTos(service.getAll());
    }

    public List<MenuTo> getAllByRestaurantId(int restaurantId) {
        return  VoteUtilsTo.getMenuTos(service.getAllByRestaurantId(restaurantId));
    }

    public Menu get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public MenuTo getTo(int id) {
        log.info("getTo {}", id);
        return VoteUtilsTo.createTo(service.get(id));
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

    public int getRestaurantId(){
        return SecurityUtil.getRestaurantId();
    }

}