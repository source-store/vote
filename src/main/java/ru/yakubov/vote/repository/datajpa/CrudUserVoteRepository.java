package ru.yakubov.vote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yakubov.vote.model.UserVote;

@Transactional(readOnly = true)
public interface CrudUserVoteRepository extends JpaRepository<UserVote, Integer> {

    @Transactional
    @Modifying
//    @Query(name = User.DELETE)
    @Query("DELETE FROM UserVote u WHERE u.id=:id")
    int delete(@Param("id") int id);

    UserVote getByEmail(String email);

}
