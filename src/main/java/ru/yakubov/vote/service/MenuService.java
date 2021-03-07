package ru.yakubov.vote.service;

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

    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Menu get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Menu> getAll() {
        return repository.getAll();
    }

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
