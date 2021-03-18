package ru.yakubov.vote.web.menu;


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
import java.util.List;

import static ru.yakubov.vote.util.DateTimeUtil.makeDateFromString;

/*
*   GET    /admin/menu/all/in?date1={date1}&date2={date2}       get menu for all restaurants for the period
*   GET    /admin/menu/{id}/in?date1={date1}&date2={date2}      get all menu items of restaurant from date
*   GET    /admin/menu/{id}                                     get all menu items of restaurant
*   GET    /admin/menu/one/{id}                                 get menu item
*   POST   /admin/menu/                                         create menu item
*   DELETE /admin/menu/{id}                                     delete menu item
*   PUT    /admin/menu/{id}                                     update menu item
*/

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuRestController{
    public static final String REST_URL = ROOT_REST_URL+ADMIN_REST_URL+MENU_REST_URL;

    public AdminMenuRestController(MenuService service) {
        super(service);
    }

    //http://localhost:8080/vote/admin/menu/all/in?date1=2021-03-08&date2=2021-03-10
    @GetMapping(value = "/all/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByDate(@RequestParam("date1") String date1, @RequestParam("date2") String date2){
        return super.GetAllByDate(makeDateFromString(date1), makeDateFromString(date2));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllByRestaurantId(@PathVariable int id) {
        return super.getAllByRestaurantId(id);
    }

    @GetMapping(value = "/one/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }


    @GetMapping(value = "/{id}/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> GetAllByRestaurantIdAndDate(@PathVariable int id, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        return super.GetAllByRestaurantIdAndDate(id, makeDateFromString(date1), makeDateFromString(date2));
    }


    //{ "restaurant":{"id":50005,"name":"Ресторан1","address":"адрес1"},"date":[2021,3,18],"decription":"menu11", "price":50 }
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> createWithLocations(@Validated(View.Web.class) @RequestBody Menu menu){
        Menu newMenu = super.create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL+"/{Id}")
                .buildAndExpand(newMenu.getRestaurant().getId(),newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        super.delete(id);
    }

    //{ "id": 100000, "restaurant": { "id": 50006 },"date": [2021, 3, 18],"decription": "m443enu11", "price": 50 }
    @PutMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Menu menu){
        super.update(menu);
    }

}
