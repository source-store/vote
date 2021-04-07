package ru.yakubov.vote.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yakubov.vote.util.ValidationUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "description", "restaurant_id"}, name = "menus_unique_date_description_restaurant_idx")})
public class Menu extends AbstractBaseEntity {


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurant;

    @NotNull
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = ValidationUtil.DATE_PATTERN)
    private LocalDate date = LocalDate.now();


    @NotNull
    @NotBlank
    @Column(name = "description", nullable = false)
    @Size(min = 3, max = 100)
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    public Menu() {
    }

    public Menu(Menu menu) {
        this(menu.getId(), menu.getDescription(), menu.getDate(), menu.getPrice());
    }

    public Menu(Integer id, String description, LocalDate date, Integer price) {
        super(id);
        this.description = description;
        this.date = date;
        this.price = price;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurants getRestaurant() {
        return restaurant;
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

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", date=" + date +
                ", description=" + description +
                ", price=" + price +
                '}';
    }
}
