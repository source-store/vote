package ru.yakubov.vote.to;


import java.time.LocalDate;
import java.util.Date;


public class MenuTo {
    private final Integer id;


    private final LocalDate date;

    private final String decription;

    private final Integer price;

    private final RestaurantTo restaurant;

    public MenuTo(Integer id, LocalDate date, String decription, Integer price, RestaurantTo restaurant) {
        this.id = id;
        this.date = date;
        this.decription = decription;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDecription() {
        return decription;
    }

    public Integer getPrice() {
        return price;
    }

    public RestaurantTo getRestaurant() {
        return restaurant;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "date=" + date +
                ", decription=" + decription +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
