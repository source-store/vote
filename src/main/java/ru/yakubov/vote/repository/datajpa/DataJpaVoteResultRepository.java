package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.VoteResult;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaVoteResultRepository {

    @Autowired
    private CrudVoteResultRepository crudRepository;

    public List<VoteResult> getResultDate(LocalDate startDate, LocalDate endDate) {
        return crudRepository.getResultDate(startDate, endDate);
    }
}
