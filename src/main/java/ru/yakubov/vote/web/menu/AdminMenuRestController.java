package ru.yakubov.vote.web.menu;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.web.View;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

/*
 *   GET    /admin/menus/in?date1={date1}&date2={date2}           get menu for all restaurants for the period
 *   GET    /admin/menus?id={id}&date1={date1}&date2={date2}      get all menu items of restaurant from date
 *   GET    /admin/menus/{id}                                     get menu item
 *   POST   /admin/menus/                                         create menu item
 *   DELETE /admin/menus/{id}                                     delete menu item
 *   PUT    /admin/menus/{id}                                     update menu item
 */

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuRestController {
    public static final String REST_URL = ROOT_REST_URL + ADMIN_REST_URL + MENU_REST_URL;

    public AdminMenuRestController(MenuService service) {
        super(service);
    }

    //http://localhost:8080/vote/admin/menus/in?date1=2021-03-08&date2=2021-03-10
    @GetMapping(value = "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByDate(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1, @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.GetAllByDate(date1, date2);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByRestaurantIdAndDate(@RequestParam("id") int id, @RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1, @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.GetAllByRestaurantIdAndDate(id, date1, date2);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }

    //{ "restaurant":{"id":50005,"name":"Ресторан1","address":"адрес1"},"date":[2021,3,18],"description":"menu11", "price":50 }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> createWithLocations(@Validated(View.Web.class) @RequestBody Menu menu) {
        Menu newMenu = create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}")
                .buildAndExpand(newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable int id) {
        delete(id);
    }

    //{ "id": 100000, "restaurant": { "id": 50006 },"date": [2021, 3, 18],"description": "m443enu11", "price": 50 }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(@PathVariable int id, @Validated(View.Web.class) @RequestBody Menu menu) {
        update(id, menu);
    }

    private void delete(int id) {
        log.info("delete menu {}", id);
        service.delete(id);
    }

    private void update(int id, Menu menu) {
        log.info("update menu id={} {}", id, menu);
        assureIdConsistent(menu, id);
        service.create(menu);
    }

    private Menu create(Menu menu) {
        log.info("create menu {}", menu);
        checkNew(menu);
        return service.create(menu);
    }
}
