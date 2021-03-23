package ru.yakubov.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.RestaurantRepository;
import ru.yakubov.vote.repository.UserVoteRepository;
import ru.yakubov.vote.repository.VoteRepository;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.util.exception.FailVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.yakubov.vote.model.Votes.VOTE_DEADLINE;
import static ru.yakubov.vote.util.ValidationUtil.*;

@Service
public class VoteService {

    private final VoteRepository repository;
    private final UserVoteRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository repository, UserVoteRepository userRepository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public VoteTo create(Votes vote) {
        Assert.notNull(vote, "vote must not be null");
        return VoteUtilsTo.createTo(repository.save(vote));
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
    public Votes getByUserOneDate(int id, LocalDate setDate) {
        return repository.getByUserOneDate(id, setDate);
    }

    @Transactional
    public List<Votes> getByUser(int id) {
        return repository.getByUser(id);
    }

    @Transactional
    public List<Votes> getByDate(LocalDate beginDate, LocalDate endDate) {
        return repository.getByDate(beginDate, endDate);
    }

    @Transactional
    public void DeleteCurrentVote(int id) {
        Votes votes = getByUserOneDate(id, LocalDate.now());
        if (votes != null) {
            delete(votes.getId());
        }
    }

    @Transactional
    public List<Votes> getByOneDate(LocalDate setDate) {
        return repository.getByDate(setDate, setDate);
    }

    public VoteTo vote(int userId, int restaurantId) {
        Votes vote = getByUserOneDate(userId, LocalDate.now());
        Restaurants restaurants = restaurantRepository.get(restaurantId);
        if (LocalTime.now().isAfter(VOTE_DEADLINE)) {
            throw new FailVoteException("Too late change vote");
        }
        if (vote == null) {
            UserVote user = userRepository.get(userId);
            Votes newVote = new Votes(LocalDate.now());
            newVote.setUserVote(user);
            newVote.setRestaurant(restaurants);
            return create(newVote);
        } else {
            vote.setRestaurant(restaurants);
            return create(vote);
        }
    }

}
