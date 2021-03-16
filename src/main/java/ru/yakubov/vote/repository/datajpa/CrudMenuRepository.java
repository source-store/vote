package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Query("DELETE FROM Menu u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT u FROM Menu u WHERE u.restaurant.id=:id ORDER BY u.date, u.decription")
    List<Menu> getAllByRestaurantId (@Param("id") int id);

    @Query("SELECT m FROM Menu m WHERE m.id=:id")
    Menu getOne(@Param("id") int id);

    @Override
    @Query("SELECT u FROM Menu u JOIN FETCH u.restaurant ORDER BY u.date, u.restaurant.name, u.decription")
    List<Menu> findAll();

    @Query("SELECT u FROM Menu u JOIN FETCH u.restaurant WHERE u.restaurant.id=:id and u.date between :beginDate and :endDate ORDER BY u.date, u.restaurant.id, u.decription")
    List<Menu> GetAllByRestaurantIdAndDate (@Param("id") int id, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT u FROM Menu u JOIN FETCH u.restaurant WHERE u.date between :beginDate and :endDate ORDER BY u.date, u.decription")
    List<Menu> GetAllByDate (@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

}
