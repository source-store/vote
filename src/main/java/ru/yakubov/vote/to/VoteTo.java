package ru.yakubov.vote.to;

import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = -4783984052255957103L;

    private LocalDate date;

    private int userId;

    private int restaurantId;

    public VoteTo(int userId, LocalDate date) {
        this(null, date);
    }

    public VoteTo(Integer id, LocalDate date, int userId, int restaurantId) {
        this(id, date);
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public VoteTo(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }
}
