package ru.yakubov.vote.repository;

import ru.yakubov.vote.model.Votes;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if not found, when updated
    Votes save(Votes vote);

    // false if not found
    boolean delete(int id);

    // null if not found
    Votes get(int id);

    List<Votes> getByRestaurant(int id);

    List<Votes> getByRestaurantDate(int id, LocalDate beginDate, LocalDate endDate);

    List<Votes> getByUserDate(int id, LocalDate beginDate, LocalDate endDate);

    Votes getByUserOneDate(int id, LocalDate date);

    List<Votes> getByUser(int id);

    List<Votes> getByDate(LocalDate beginDate, LocalDate endDate);


}
