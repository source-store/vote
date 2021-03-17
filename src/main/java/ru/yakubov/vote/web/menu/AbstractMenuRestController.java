package ru.yakubov.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;
import ru.yakubov.vote.web.RestUrlPattern;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.DateTimeUtil.makeDateFromString;
import static ru.yakubov.vote.util.ValidationUtil.checkNew;

public class AbstractMenuRestController extends RestUrlPattern {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected final MenuService service;

    public AbstractMenuRestController(MenuService service) {
        this.service = service;
    }

    public Menu get(int id) {
        log.info("get  id {} ", id);
        return service.get(id);
    }

    public void delete(int id){
        log.info("delete menu {}", id);
        service.delete(id);
    }

    public void update(@RequestBody Menu menu){
        log.info("update menu {}",menu);
        service.create(menu);
    }

    public Menu create(Menu menu) {
        log.info("create menu {}",menu);
        checkNew(menu);
        return service.create(menu);
    }

    public List<Menu> GetAllByDate(LocalDate date1, LocalDate date2) {
        log.info("GetAllByDate date1 {}  date2 {}", date1, date2);
        return service.GetAllByDate(date1, date2);
    }

    public List<Menu> getAllByRestaurantId(int id) {
        log.info("getAllByRestaurantId  RestaurantId {} ", id);
        return service.getAllByRestaurantId(id);
    }


    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate date1, LocalDate date2) {
        log.info("GetAllByRestaurantIdAndDate  RestaurantId {} date1 {}  date2 {}", id, date1, date2);
        return service.GetAllByRestaurantIdAndDate(id, date1, date2);
    }


}
