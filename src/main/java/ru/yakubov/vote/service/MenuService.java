package ru.yakubov.vote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.MenuVoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuVoteRepository repository;

    public MenuService(MenuVoteRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public Menu get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    @Cacheable("menu")
    public List<Menu> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    public void update(Menu menu) {
        Assert.notNull(menu, "restaurant must not be null");
        checkNotFoundWithId(repository.save(menu), menu.id());
    }

    @Transactional
    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return repository.getAllByRestaurantId(restaurantId);
    }

    @Transactional
    public Integer getIdRestaurant(int id) {
        return repository.getIdRestaurant(id);
    }

    @Transactional
    public List<Menu> GetAllByDate(LocalDate beginDate, LocalDate endDate) {
        return repository.GetAllByDate(beginDate, endDate);
    }

    @Transactional
    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate beginDate, LocalDate endDate) {
        return repository.GetAllByRestaurantIdAndDate(id, beginDate, endDate);
    }

}
