package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.UserVoteRepository;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public class DataJpaUserVoteRepository implements UserVoteRepository {

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserVoteRepository crudRepository;

    @Override
    @Transactional
    public UserVote save(UserVote userVote) {
        return crudRepository.save(userVote);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public UserVote get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public UserVote getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<UserVote> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }
}
