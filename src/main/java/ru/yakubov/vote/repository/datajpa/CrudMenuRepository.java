package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:id ORDER BY m.date, m.description")
    List<Menu> getAllByRestaurantId(@Param("id") int id);

    @Override
    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant ORDER BY m.date, m.restaurant.name, m.description")
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant WHERE m.restaurant.id=:id AND m.date BETWEEN :beginDate AND :endDate ORDER BY m.date, m.restaurant.id, m.description")
    List<Menu> GetAllByRestaurantIdAndDate(@Param("id") int id, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant WHERE m.date BETWEEN :beginDate AND :endDate ORDER BY m.date, m.description")
    List<Menu> GetAllByDate(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

}
