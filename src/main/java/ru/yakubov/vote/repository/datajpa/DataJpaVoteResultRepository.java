package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.repository.VoteResultRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteResultRepository implements VoteResultRepository {

    @Autowired
    private CrudVoteResultRepository crudRepository;

    @Override
    public List<VoteResult> getResultDate(LocalDate startDate, LocalDate endDate) {
        return crudRepository.getResultDate(startDate, endDate);
    }
}
