package ru.yakubov.vote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.UserVoteRepository;

import java.util.List;

import static ru.yakubov.vote.util.ValidationUtil.checkNotFound;
import static ru.yakubov.vote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserVoteService {

    private final UserVoteRepository repository;

    public UserVoteService(UserVoteRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserVote create(UserVote userVote) {
        Assert.notNull(userVote, "user must not be null");
        return repository.save(userVote);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public UserVote get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public UserVote getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<UserVote> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(UserVote userVote) {
        Assert.notNull(userVote, "user must not be null");
        checkNotFoundWithId(repository.save(userVote), userVote.id());
    }


}
