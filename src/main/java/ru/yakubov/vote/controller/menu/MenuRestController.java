package ru.yakubov.vote.controller.menu;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.controller.RestUrlPattern.MENU_REST_URL;
import static ru.yakubov.vote.controller.RestUrlPattern.ROOT_REST_URL;

/**
 * GET    /rest/menus/today                                   get menu for today
 * GET    /rest/menus/{id}                                    get menu item
 * GET    /rest/menus/in?beginDate={beginDate}&endDate={endDate}          get menu for all restaurants for the period
 * GET    /rest/menus?id={id}&beginDate={beginDate}&endDate={endDate}     get all menu items of restaurant from date
 */


//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractMenuRestController {
    public static final String REST_URL = ROOT_REST_URL + MENU_REST_URL;

    public MenuRestController(MenuService service) {
        super(service);
    }

    // /rest/menus/today                                         get menu for today
    @Cacheable("menus")
    @GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getTodayMenu() {
        return super.GetAllByDate(LocalDate.now(), LocalDate.now());
    }

    // /rest/menus/{id}                                    get menu item
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }

    // /rest/menus/in?beginDate={beginDate}&endDate={endDate}          get menu for all restaurants for the period
    @GetMapping(value = "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByDate(@RequestParam("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return super.GetAllByDate(beginDate, endDate);
    }

    // /rest/menus?id={id}&beginDate={beginDate}&endDate={endDate}     get all menu items of restaurant from date
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByRestaurantIdAndDate(@RequestParam("id") int id,
                                                  @RequestParam("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return super.GetAllByRestaurantIdAndDate(id, beginDate, endDate);
    }

}
