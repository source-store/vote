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
    private final String description;

    @NotNull
    private final Integer price;

    private final RestaurantTo restaurant;

    public MenuTo(Integer id, LocalDate date, String description, Integer price, RestaurantTo restaurant) {
        super(id);
        this.date = date;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
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
                ", description=" + description +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
