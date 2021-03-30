package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.UserVote;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudUserVoteRepository extends JpaRepository<UserVote, Integer> {

    @Modifying
    @Query("DELETE FROM UserVote u WHERE u.id=:id")
    int delete(@Param("id") int id);

    UserVote getByEmail(String email);


    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM UserVote u ORDER BY u.name")
    List<UserVote> getByRoles();


}
