package ru.yakubov.vote.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.MenuTestData;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.repository.MenuVoteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryMenuRepository extends InMemoryBaseRepository<Menu>  implements MenuVoteRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMenuRepository.class);

    public void init()
    {
        map.clear();
        put(MenuTestData.MENU1);
        put(MenuTestData.MENU2);
        put(MenuTestData.MENU3);
        put(MenuTestData.MENU6);
        put(MenuTestData.MENU7);
        counter.getAndSet(MenuTestData.MENU7.getId()+1);
    }

    @Override
    public List<Menu> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return map.values().stream().filter(mnu -> mnu.getRestaurant().getId()==restaurantId).collect(Collectors.toList());
    }

    @Override
    public Integer getIdRestaurant(int id) {
        return map.values().stream().filter(mnu -> mnu.getId()==id).findFirst().get().getRestaurant().getId();
    }

    @Override
    public List<Menu> GetAllByDate(LocalDate beginDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<Menu> GetAllByRestaurantIdAndDate(int id, LocalDate beginDate, LocalDate endDate) {
        return null;
    }
}
