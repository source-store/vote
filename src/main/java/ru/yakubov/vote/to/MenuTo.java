package ru.yakubov.vote.to;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;


public class MenuTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = -4783984052914957103L;

    @NotNull
    private final LocalDate date;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private final String decription;

    @NotNull
    private final Integer price;

    private final RestaurantTo restaurant;

    public MenuTo(Integer id, LocalDate date, String decription, Integer price, RestaurantTo restaurant) {
        super(id);
        this.date = date;
        this.decription = decription;
        this.price = price;
        this.restaurant = restaurant;
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
