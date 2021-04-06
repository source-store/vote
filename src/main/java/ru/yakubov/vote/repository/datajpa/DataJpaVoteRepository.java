package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Votes;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaVoteRepository {

    @Autowired
    private CrudVoteRepository crudRepository;

    public Votes save(Votes vote) {
        if (!vote.isNew() && get(vote.getId()) == null) {
            return null;
        }
        return crudRepository.save(vote);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Votes get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        return crudRepository.getByUserDate(id, beginDate, endDate);
    }

    public Votes getByUserOneDate(int id, LocalDate date) {
        return crudRepository.getByUserDate(id, date, date).stream().findFirst().orElse(null);
    }
}
