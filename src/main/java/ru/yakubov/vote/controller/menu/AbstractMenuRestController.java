package ru.yakubov.vote.controller.menu;

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

    public List<Menu> GetAllByDate(LocalDate beginDate, LocalDate endDate) {
        log.info("GetAllByDate beginDate {}  endDate {}", beginDate, endDate);
        return service.GetAllByDate(beginDate, endDate);
    }

    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate beginDate, LocalDate endDate) {
        log.info("GetAllByRestaurantIdAndDate  RestaurantId {} beginDate {}  endDate {}", id, beginDate, endDate);
        return service.GetAllByRestaurantIdAndDate(id, beginDate, endDate);
    }


}
