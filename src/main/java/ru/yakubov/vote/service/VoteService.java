package ru.yakubov.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.model.Votes;
import ru.yakubov.vote.repository.RestaurantRepository;
import ru.yakubov.vote.repository.UserVoteRepository;
import ru.yakubov.vote.repository.VoteRepository;
import ru.yakubov.vote.repository.VoteResultRepository;
import ru.yakubov.vote.to.VoteTo;
import ru.yakubov.vote.util.VoteUtilsTo;
import ru.yakubov.vote.util.exception.FailVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.yakubov.vote.model.Votes.VOTE_DEADLINE;
import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository repository;
    private final UserVoteRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteResultRepository voteResultRepository;

    public VoteService(VoteRepository repository, UserVoteRepository userRepository,
                       RestaurantRepository restaurantRepository,
                       VoteResultRepository voteResultRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteResultRepository = voteResultRepository;
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
    public List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate) {
        return repository.getByUserDate(id, beginDate, endDate);
    }

    @Transactional
    public Votes getByUserOneDate(int id, LocalDate setDate) {
        return repository.getByUserOneDate(id, setDate);
    }

    public VoteTo vote(int userId, int restaurantId) {
        Votes vote = getByUserOneDate(userId, LocalDate.now());
        if (vote == null) {
            UserVote user = userRepository.get(userId);
            Votes newVote = new Votes(LocalDate.now());
            newVote.setUserVote(user);
            Restaurants restaurants = restaurantRepository.get(restaurantId);
            newVote.setRestaurant(restaurants);
            return create(newVote);
        } else {
            if (LocalTime.now().isAfter(VOTE_DEADLINE)) {
                throw new FailVoteException("Too late change vote");
            }
            Restaurants restaurants = restaurantRepository.get(restaurantId);
            vote.setRestaurant(restaurants);
            return create(vote);
        }
    }

    public List<VoteResult> getResultDate(LocalDate beginDate, LocalDate endDate) {
        return voteResultRepository.getResultDate(beginDate, endDate);
    }
}
