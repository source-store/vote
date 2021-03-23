package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaVoteRepository implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudRepository;

    @Override
    @Transactional
    public Votes save(Votes vote) {
        if (!vote.isNew() && get(vote.getId()) == null) {
            return null;
        }
        return crudRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    @Transactional
    public Votes get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public List<Votes> getByRestaurant(int id) {
        return crudRepository.getByRestaurant(id);
    }

    @Override
    public List<Votes> getByRestaurantDate(int id, LocalDate beginDate, LocalDate endDate) {
        return crudRepository.getByRestaurantDate(id, beginDate, endDate);
    }

    @Override
    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        return crudRepository.getByUserDate(id, beginDate, endDate);
    }

    @Override
    public Votes getByUserOneDate(int id, LocalDate date) {
        return crudRepository.getByUserDate(id, date, date).stream().findFirst().orElse(null);
    }


    @Override
    public List<Votes> getByUser(int id) {
        return crudRepository.getByUser(id);
    }

    @Override
    public List<Votes> getByDate(LocalDate beginDate, LocalDate endDate) {
        return crudRepository.getByDate(beginDate, endDate);
    }
}
