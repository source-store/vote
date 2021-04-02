package ru.yakubov.vote.controller.menu;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.controller.RestUrlPattern;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.controller.View;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.assureIdConsistent;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

/**
 * GET    /rest/admin/menus/in?date1={date1}&date2={date2}           get menu for all restaurants for the period
 * GET    /rest/admin/menus?id={id}&date1={date1}&date2={date2}      get all menu items of restaurant from date
 * GET    /rest/admin/menus/{id}                                     get menu item
 * POST   /rest/admin/menus/                                         create menu item
 * DELETE /rest/admin/menus/{id}                                     delete menu item
 * PUT    /rest/admin/menus/{id}                                     update menu item
 */

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuRestController {
    public static final String REST_URL = RestUrlPattern.ROOT_REST_URL + RestUrlPattern.ADMIN_REST_URL + RestUrlPattern.MENU_REST_URL;

    public AdminMenuRestController(MenuService service) {
        super(service);
    }

    // /rest/admin/menus/in?date1={date1}&date2={date2}           get menu for all restaurants for the period
    @GetMapping(value = "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByDate(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                   @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.GetAllByDate(date1, date2);
    }

    // /rest/admin/menus?id={id}&date1={date1}&date2={date2}      get all menu items of restaurant from date
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByRestaurantIdAndDate(@RequestParam("id") int id,
                                                  @RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                  @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return super.GetAllByRestaurantIdAndDate(id, date1, date2);
    }

    // /rest/admin/menus/{id}                                     get menu item
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }


    // /rest/admin/menus/                                         create menu item
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> createWithLocations(@Validated(View.Web.class) @RequestBody Menu menu) {
        Menu newMenu = create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}")
                .buildAndExpand(newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    // /rest/admin/menus/{id}                                     delete menu item
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable int id) {
        delete(id);
    }

    // /rest/admin/menus/{id}                                     update menu item
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(@PathVariable int id,
                           @Validated(View.Web.class)
                           @RequestBody Menu menu) {
        update(menu, id);
    }

    private void delete(int id) {
        log.info("delete menu {}", id);
        service.delete(id);
    }

    private void update(Menu menu, int id) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        service.create(menu);
    }

    private Menu create(Menu menu) {
        log.info("create menu {}", menu);
        checkNew(menu);
        return service.create(menu);
    }
}
