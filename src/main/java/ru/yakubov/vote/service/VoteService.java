package ru.yakubov.vote.service;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Votes create(Votes vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public Votes get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    public List<Votes> getByRestaurant(int id) {
        return repository.getByRestaurant(id);
    }

    @Transactional
    public List<Votes> getByRestaurantDate(int id, LocalDate beginDate, LocalDate endDate) {
        return repository.getByRestaurantDate(id, beginDate, endDate);
    }

    @Transactional
    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        return repository.getByUserDate(id, beginDate, endDate);
    }

    @Transactional
    public List<Votes> getByUser(int id) {
        return repository.getByUser(id);
    }

    @Transactional
    public List<Votes> getByDate(LocalDate beginDate, LocalDate endDate) {
        return repository.getByDate(beginDate, endDate);
    }

}
