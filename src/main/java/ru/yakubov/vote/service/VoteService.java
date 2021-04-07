package ru.yakubov.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.controller.SecurityUtil;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.datajpa.DataJpaRestaurantRepository;
import ru.yakubov.vote.repository.datajpa.DataJpaVoteRepository;
import ru.yakubov.vote.repository.datajpa.DataJpaVoteResultRepository;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.util.exception.FailVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class VoteService {
    public static final LocalTime VOTE_DEADLINE = LocalTime.of(11, 0);

    private final DataJpaVoteRepository repository;
    private final DataJpaRestaurantRepository restaurantRepository;
    private final DataJpaVoteResultRepository voteResultRepository;

    public VoteService(DataJpaVoteRepository repository,
                       DataJpaRestaurantRepository restaurantRepository,
                       DataJpaVoteResultRepository voteResultRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.voteResultRepository = voteResultRepository;
    }

    @Transactional
    public VoteTo create(Votes votes) {
        Assert.notNull(votes, "vote must not be null");
        return VoteUtilsTo.createTo(repository.save(votes));
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Votes get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        return repository.getByUserDate(id, beginDate, endDate);
    }

    public Votes getByUserOneDate(int id, LocalDate setDate) {
        return repository.getByUserOneDate(id, setDate);
    }

    @Transactional
    public VoteTo vote(int userId, int restaurantId) {
        Votes votes = getByUserOneDate(userId, LocalDate.now());
        if (votes != null) {
            throw new FailVoteException("User voted today");
        }
        votes = new Votes(LocalDate.now());
        votes.setUser(SecurityUtil.safeGet().getUser());
        Restaurants restaurants = restaurantRepository.getOne(restaurantId);
        Assert.notNull(restaurants, "restaurants must not be null");
        votes.setRestaurant(restaurants);
        return VoteUtilsTo.createTo(repository.save(votes));
    }

    @Transactional
    public VoteTo updateVote(int userId, int restaurantId) {
        if (LocalTime.now().isAfter(VOTE_DEADLINE)) {
            throw new FailVoteException("Too late change vote");
        }
        Votes votes = getByUserOneDate(userId, LocalDate.now());
        if (votes == null) {
            throw new FailVoteException("User did not vote today");
        }
        Restaurants restaurants = restaurantRepository.getOne(restaurantId);
        Assert.notNull(restaurants, "restaurants must not be null");
        votes.setRestaurant(restaurants);
        return VoteUtilsTo.createTo(repository.save(votes));
    }

    public List<VoteResult> getResultDate(LocalDate beginDate, LocalDate endDate) {
        return voteResultRepository.getResultDate(beginDate, endDate);
    }
}
