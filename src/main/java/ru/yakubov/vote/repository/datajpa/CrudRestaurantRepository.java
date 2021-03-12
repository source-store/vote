package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Restaurants;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurants, Integer> {

//    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurants m WHERE m.id=:id")
    int delete(@Param("id") int id);

//    @Transactional
    @Query("SELECT m FROM Restaurants m ORDER BY m.name DESC")
    List<Restaurants> findAll();

//    @Transactional
    @Query("SELECT m FROM Restaurants m WHERE m.id=:id")
    Restaurants getOne(@Param("id") int id);
}
