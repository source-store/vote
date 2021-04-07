package ru.yakubov.vote.repository.datajpa;


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
    @Query("DELETE FROM Votes v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT v FROM Votes v WHERE v.id=:id")
    Votes get(@Param("id") int id);

    @Query("SELECT v FROM Votes v JOIN FETCH v.userVote WHERE v.userVote.id=:id AND v.date BETWEEN :beginDate AND :endDate ORDER BY v.date, v.restaurant.id")
    List<Votes> getByUserDate(@Param("id") int id, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT v FROM Votes v JOIN FETCH v.userVote WHERE v.userVote.id=:id ORDER BY v.date, v.restaurant.id")
    List<Votes> getByUser(@Param("id") int id);

    @Query("SELECT v FROM Votes v JOIN FETCH v.userVote WHERE v.date BETWEEN :beginDate AND :endDate  ORDER BY v.date, v.restaurant.id")
    List<Votes> getByDate(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);
}
