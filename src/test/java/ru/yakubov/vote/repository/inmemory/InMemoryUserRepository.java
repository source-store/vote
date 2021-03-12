package ru.yakubov.vote.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.yakubov.vote.UserTestData;
import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.UserVoteRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<UserVote> implements UserVoteRepository {


    public void init(){
        map.clear();
        put(UserTestData.user1);
        put(UserTestData.user2);
        put(UserTestData.user3);
        counter.getAndSet(UserTestData.USER_ID3+1);
    }

    @Override
    public UserVote getByEmail(String email) {
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<UserVote> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(UserVote::getName).thenComparing(UserVote::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserVote> getByRoles(Role role) {
        return null;
    }
}
