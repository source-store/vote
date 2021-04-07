package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    User getByEmail(String email);

    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM User u ORDER BY u.name")
    List<User> getByRoles();
}
