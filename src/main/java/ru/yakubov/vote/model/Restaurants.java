package ru.yakubov.vote.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurants extends AbstractNamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String address;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnore
    private List<Votes> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnore
    private List<Menu> menu;


    public Restaurants() {
    }

    public Restaurants(Restaurants r) {
        this(r.getId(), r.getName(), r.getAddress());
    }

    public Restaurants(Integer id, String name, @NotBlank @Size(min = 1, max = 100) String address) {
        super(id, name);
        this.address = address;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public void setVotes(List<Votes> votes) {
        this.votes = votes;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public List<Votes> getVotes() {
        return votes;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Restaurants{" +
                "id=" + id +
                ", name=" + name +
                ", address=" + address +
                '}';
    }
}
