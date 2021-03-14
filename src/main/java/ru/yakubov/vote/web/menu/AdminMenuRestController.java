package ru.yakubov.vote.web.menu;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController {
    public static final String REST_URL = "/admin/menu";

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private final MenuService service;

    public AdminMenuRestController(MenuService service) {
        this.service = service;
    }

    //http://localhost:8080/vote/admin/menu/50005/by?date=2021-03-10
    @GetMapping(value = "/{id}/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getMenuByOneDate(@PathVariable int id, @RequestParam String date){
        log.info("getMenuByOneDate {} by date{ }", date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate setDate = LocalDate.parse(date, formatter);
        return service.GetAllByRestaurantIdAndDate(id, setDate, setDate);
    }


    //{ "restaurant":{"id":50005,"name":"Ресторан1","address":"адрес1"},"date":[2021,3,18],"decription":"menu11", "price":50 }
    @PostMapping(value = "/new", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocations(@RequestBody Menu menu){
        log.info("create menu {}",menu);
        checkNew(menu);
        Menu newMenu =service.create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL+"/{Id}")
                .buildAndExpand(newMenu.getRestaurant().getId(),newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        log.info("delete menu {}", id);
        service.delete(id);
    }


    //{ "id": 100000, "restaurant": { "id": 50006 },"date": [2021, 3, 18],"decription": "m443enu11", "price": 50 }
    @PutMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu){
        log.info("update menu {}",menu);
        service.create(menu);
    }

}
