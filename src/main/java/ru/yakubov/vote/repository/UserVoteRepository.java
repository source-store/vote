package ru.yakubov.vote.repository;

import ru.yakubov.vote.model.UserVote;

import java.util.List;

public interface UserVoteRepository {
    // null if not found, when updated
    UserVote save(UserVote userVote);

    // false if not found
    boolean delete(int id);

    // null if not found
    UserVote get(int id);

    // null if not found
    UserVote getByEmail(String email);

    List<UserVote> getAll();
}
