package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.repository.UserVoteRepository;

import java.util.List;
import java.util.stream.Collectors;


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
    @Transactional
    public UserVote get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public UserVote getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public List<UserVote> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    @Transactional
    public List<UserVote> getByRoles(Role role){
        return crudRepository.getByRoles().stream().filter(u -> u.getRoles().contains(role)).collect(Collectors.toList());
    }
}
