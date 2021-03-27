package ru.yakubov.vote.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yakubov.vote.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "decription", "restaurant_id"}, name = "menu_unique_date_decription_restaurant_idx")})
public class Menu extends AbstractBaseEntity {


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurant;

    @NotNull
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate date = LocalDate.now();


    @NotNull
    @NotBlank
    @Column(name = "decription", nullable = false)
    @Size(min = 3, max = 100)
    private String decription;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    public Menu() {
    }

    public Menu(Menu menu) {
        this(menu.getId(), menu.getDecription(), menu.getDate(), menu.getPrice());
    }

    public Menu(Integer id, String decription, LocalDate date, Integer price) {
        super(id);
        this.decription = decription;
        this.date = date;
        this.price = price;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDecription(String decription) {
        this.decription = decription;
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

    public String getDecription() {
        return decription;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", date=" + date +
                ", decription=" + decription +
                ", price=" + price +
                '}';
    }
}
