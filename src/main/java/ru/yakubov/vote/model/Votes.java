package ru.yakubov.vote.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_unique_user_date_idx")})
public class Votes extends AbstractBaseEntity{
    public static final LocalTime VOTE_DEADLINE = LocalTime.of(11, 0);

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserVote userVote;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurants restaurant;


    public Votes(Votes votes) {
        this(votes.getId(), votes.getDate());
        this.setRestaurant(votes.getRestaurant());
        this.setUserVote(votes.getUserVote());
    }

    public Votes(LocalDate date) {
        this(null, date);
    }

    public Votes(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public Votes() {
    }

    public LocalDate getDate() {
        return date;
    }

    public UserVote getUserVote() {
        return userVote;
    }

    public Restaurants getRestaurant() {
        return restaurant;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUserVote(UserVote userVote) {
        this.userVote = userVote;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

}
