package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.VoteResult;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteResultRepository extends JpaRepository<VoteResult, Integer> {

    @Query("SELECT u FROM VoteResult u WHERE u.date between :beginDate and :endDate")
    List<VoteResult> getResultDate(@Param("beginDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
