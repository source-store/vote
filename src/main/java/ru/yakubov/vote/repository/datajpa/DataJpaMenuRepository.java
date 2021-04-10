package ru.yakubov.vote.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaMenuRepository {

    private final CrudMenuRepository crudMenuRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Menu save(Menu menu) {
        //проверку на наличие обновляемой записи надо составить
        if (!menu.isNew() && get(menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getOne(menu.getRestaurant().id()));
        return crudMenuRepository.save(menu);
    }

    public boolean delete(int id) {
        return crudMenuRepository.delete(id) != 0;
    }

    public Menu get(int id) {
        return crudMenuRepository.findById(id).orElse(null);

    }

    public List<Menu> getAll() {
        return crudMenuRepository.findAll();
    }

    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return crudMenuRepository.getAllByRestaurantId(restaurantId);
    }

    public List<Menu> GetAllByDate(LocalDate beginDate, LocalDate endDate) {
        return crudMenuRepository.GetAllByDate(beginDate, endDate);
    }

    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate beginDate, LocalDate endDate) {
        return crudMenuRepository.GetAllByRestaurantIdAndDate(id, beginDate, endDate);
    }

}
