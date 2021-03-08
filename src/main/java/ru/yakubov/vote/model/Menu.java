package ru.yakubov.vote.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@NamedQueries({
//        @NamedQuery(name = Menu.ALL_SORTED, query = "SELECT m FROM Menu m WHERE m.restaurant.id=:restorauntId ORDER BY m.decription DESC"),
        @NamedQuery(name = Menu.GET_ONE, query = "SELECT m FROM Menu m WHERE m.id=:id"),
        @NamedQuery(name = Menu.DELETE, query = "DELETE FROM Menu m WHERE m.id=:id")})
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "decription", "restaurant_id"}, name = "menu_unique_date_decription_restaurant_idx")})
public class Menu extends AbstractBaseEntity {

    public static final String GET_ONE = "Menu.getOne";
    //    public static final String ALL_SORTED = "Menu.getAll";
    public static final String DELETE = "Menu.delete";


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurant;


    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


    @NotNull
    @NotBlank
    @Column(name = "decription", nullable = false)
    @Size(min = 3, max = 100)
    private String decription;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    public Menu() {
    }

    public Menu(Menu menu) {
        this(menu.getId(), menu.getDecription(), menu.getDate(), menu.getRestaurant(), menu.getPrice());
    }

    public Menu(Integer id, String decription, LocalDate date, Restaurants restaurant, Long price) {
        super(id);
        this.decription = decription;
        this.date = date;
        this.restaurant = restaurant;
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

    public void setPrice(Long price) {
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

    public Long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", date=" + date +
                ", decription=" + decription +
                ", price=" + price +
//                ", restaurant=" + restaurant +
                '}';
    }
}
