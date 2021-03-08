package ru.yakubov.vote.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.MenuVoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaMenuRepository implements MenuVoteRepository {

    private final CrudMenuRepository crudMenuRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    @Transactional
    public Menu save(Menu menu) {
        if (!menu.isNew() && get(menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getOne(menu.getRestaurant().id()));
        return crudMenuRepository.save(menu);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crudMenuRepository.delete(id) != 0;
    }

    @Override
    @Transactional
    public Menu get(int id) {
        return crudMenuRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<Menu> getAll() {
        return crudMenuRepository.findAll();
    }

    @Override
    @Transactional
    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return crudMenuRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Integer getIdRestaurant(int id) {
        return crudMenuRepository.getOne(id).getRestaurant().getId();
    }
}
