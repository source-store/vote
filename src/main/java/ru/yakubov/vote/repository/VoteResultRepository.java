package ru.yakubov.vote.repository;

import ru.yakubov.vote.model.VoteResult;

import java.time.LocalDate;
import java.util.List;

public interface VoteResultRepository {

    List<VoteResult> getResultDate(LocalDate startDate, LocalDate endDate);

}
