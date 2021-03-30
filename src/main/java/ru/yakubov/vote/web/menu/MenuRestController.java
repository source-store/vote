package ru.yakubov.vote.web.menu;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;

import java.time.LocalDate;
import java.util.List;

/*
 *   GET    /menus/in?date1={date1}&date2={date2}          get menu for all restaurants for the period
 *   GET    /menus?id={id}&date1={date1}&date2={date2}     get all menu items of restaurant from date
 *   GET    /menus/{id}                                    get menu item
 */


//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractMenuRestController {
    public static final String REST_URL = ROOT_REST_URL + MENU_REST_URL;

    public MenuRestController(MenuService service) {
        super(service);
    }

    @GetMapping(value = "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByDate(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1, @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.GetAllByDate(date1, date2);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByRestaurantIdAndDate(@RequestParam("id") int id, @RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1, @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.GetAllByRestaurantIdAndDate(id, date1, date2);
    }

}
