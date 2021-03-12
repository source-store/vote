package ru.yakubov.vote.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yakubov.vote.VoteTestData;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.VoteRepository;
import ru.yakubov.vote.util.Util;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryVoteRepository extends InMemoryBaseRepository<Votes> implements VoteRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryVoteRepository.class);

    public void init()
    {
        map.clear();
        put(VoteTestData.VOTE1);
        put(VoteTestData.VOTE2);
        put(VoteTestData.VOTE3);
        put(VoteTestData.VOTE4);
        counter.getAndSet(VoteTestData.VOTE_ID4+1);
    }


    @Override
    public Votes save(Votes vote) {
        return super.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return super.delete(id);
    }

    @Override
    public Votes get(int id) {
        return super.get(id);
    }

    @Override
    public List<Votes> getByRestaurant(int id) {
        return getCollection().stream().filter(v -> v.getRestaurant().getId() == id).collect(Collectors.toList());
    }

    @Override
    public List<Votes> getByRestaurantDate(int id, LocalDate beginDate, LocalDate endDate) {
        return getCollection().stream()
                .filter(vote -> Util.isBetweenHalfOpen(vote.getDate(), beginDate, endDate) && (vote.getRestaurant().getId() == id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        return getCollection().stream()
                .filter(vote -> Util.isBetweenHalfOpen(vote.getDate(), beginDate, endDate) && (vote.getUserVote().getId() == id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Votes> getByUser(int id) {
        return getCollection().stream()
                .filter(vote -> vote.getUserVote().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Votes> getByDate(LocalDate beginDate, LocalDate endDate) {
        return getCollection().stream()
                .filter(vote -> Util.isBetweenHalfOpen(vote.getDate(), beginDate, endDate))
                .collect(Collectors.toList());
    }
}
