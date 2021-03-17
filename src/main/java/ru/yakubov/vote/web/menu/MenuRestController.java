package ru.yakubov.vote.web.menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;

import java.util.List;

import static ru.yakubov.vote.util.DateTimeUtil.makeDateFromString;

/*
 *   GET    /menu/all/in?date1={date1}&date2={date2}       get menu for all restaurants for the period
 *   GET    /menu/{id}/in?date1={date1}&date2={date2}      get all menu items of restaurant from date
 *   GET    /menu/{id}                                     get all menu items of restaurant
 *   GET    /menu/one/{id}                                 get menu item
 */


//Отдаем сообщения в формате json (produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController extends AbstractMenuRestController{
    public static final String REST_URL = ROOT_REST_URL+MENU_REST_URL;

    public MenuRestController(MenuService service) {
        super(service);
    }

    @GetMapping(value = "/all/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByDate(@RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        return super.GetAllByDate(makeDateFromString(date1), makeDateFromString(date2));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllByRestaurantId(@PathVariable int id) {
        return super.getAllByRestaurantId(id);
    }

    @GetMapping(value = "/{id}/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByRestaurantIdAndDate(@PathVariable int id, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        return super.GetAllByRestaurantIdAndDate(id, makeDateFromString(date1), makeDateFromString(date2));
    }

}
