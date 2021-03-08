package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("SELECT u FROM Menu u WHERE u.restaurant.id=:id ORDER BY u.date, u.decription")
    List<Menu> getAllByRestaurantId (@Param("id") int id);

    @Transactional
    @Query("SELECT m FROM Menu m WHERE m.id=:id")
    Menu getOne(@Param("id") int id);


}
