package ru.yakubov.vote.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.yakubov.vote.util.ValidationUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "voteresult")
public class VoteResult extends AbstractBaseEntity {

    @NotNull
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = ValidationUtil.DATE_PATTERN)
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurants;

    @NotNull
    private int voteCount;

    @NotNull
    private int menuCount;

    public VoteResult(Integer id, @NotNull LocalDate date, @NotNull int voteCount, @NotNull int menuCount) {
        super(id);
        this.date = date;
        this.voteCount = voteCount;
        this.menuCount = menuCount;
    }

    public VoteResult() {
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getMenuCount() {
        return menuCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
