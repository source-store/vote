package ru.yakubov.vote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.MenuVoteRepository;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuVoteRepository repository;

    public MenuService(MenuVoteRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "menu", allEntries = true)
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Cacheable("menu")
    public Menu get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("menu")
    public List<Menu> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void update(Menu menu) {
        Assert.notNull(menu, "restaurant must not be null");
        checkNotFoundWithId(repository.save(menu), menu.id());
    }

    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return repository.getAllByRestaurantId(restaurantId);
    }

    public Integer getIdRestaurant(int id){
        return repository.getIdRestaurant(id);
    }
}
