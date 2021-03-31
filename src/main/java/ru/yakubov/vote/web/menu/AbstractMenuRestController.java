package ru.yakubov.vote.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.service.MenuService;

import java.time.LocalDate;
import java.util.List;

public class AbstractMenuRestController {
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

    public List<Menu> GetAllByDate(LocalDate date1, LocalDate date2) {
        log.info("GetAllByDate date1 {}  date2 {}", date1, date2);
        return service.GetAllByDate(date1, date2);
    }

    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate date1, LocalDate date2) {
        log.info("GetAllByRestaurantIdAndDate  RestaurantId {} date1 {}  date2 {}", id, date1, date2);
        return service.GetAllByRestaurantIdAndDate(id, date1, date2);
    }


}
