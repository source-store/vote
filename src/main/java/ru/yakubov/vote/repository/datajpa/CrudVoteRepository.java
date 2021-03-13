package ru.yakubov.vote.repository.datajpa;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Votes;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Votes, Integer> {

    @Modifying
    @Query("DELETE FROM Votes u WHERE u.id=:id")
    int delete(@Param("id") int id);


//    @EntityGraph(attributePaths = {"restaurant"})
    @Query("SELECT u FROM Votes u " +
            "WHERE u.id=:id")
    Votes get(@Param("id") int id);


    @Query("SELECT m FROM Votes m JOIN FETCH m.restaurant WHERE m.restaurant.id=:id ORDER BY m.date")
    List<Votes> getByRestaurant(@Param("id") int id);

    @Query("SELECT m FROM Votes m JOIN FETCH m.restaurant WHERE m.restaurant.id=:id and m.date between :beginDate and :endDate ORDER BY m.date, m.restaurant.id")
    List<Votes> getByRestaurantDate(@Param("id") int id, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT m FROM Votes m JOIN FETCH m.userVote WHERE m.userVote.id=:id and m.date between :beginDate and :endDate ORDER BY m.date, m.restaurant.id")
    Votes getByUserDate(@Param("id") int id, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM Votes m JOIN FETCH m.userVote WHERE m.userVote.id=:id ORDER BY m.date, m.restaurant.id")
    List<Votes> getByUser(@Param("id") int id);

    @Query("SELECT m FROM Votes m JOIN FETCH m.userVote WHERE m.date between :beginDate and :endDate  ORDER BY m.date, m.restaurant.id")
    List<Votes> getByDate(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

}
