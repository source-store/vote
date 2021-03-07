package ru.yakubov.vote.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.MenuVoteRepository;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaMenuRepository implements MenuVoteRepository {

    private final CrudMenuRepository crudMenuRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository) {
        this.crudMenuRepository = crudMenuRepository;
    }


    @Override
    @Transactional
    public Menu save(Menu menu) {
        return crudMenuRepository.save(menu);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crudMenuRepository.delete(id) != 0;
    }

    @Override
    public Menu get(int id) {
        return crudMenuRepository.getOne(id);
    }

    @Override
    public List<Menu> getAll() {
        return crudMenuRepository.findAll();
    }

    @Override
    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return crudMenuRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Integer getIdRestaurant(int id) {
        return crudMenuRepository.getOne(id).getRestaurant().getId();
    }
}
