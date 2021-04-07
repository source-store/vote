package ru.yakubov.vote.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.User;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@Transactional(readOnly = true)
public class DataJpaUserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository crudRepository;

    public User save(User user) {
        return crudRepository.save(user);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    public List<User> getByRoles(Role role) {
        return crudRepository.getByRoles().stream().filter(u -> u.getRoles().contains(role)).collect(Collectors.toList());
    }
}
