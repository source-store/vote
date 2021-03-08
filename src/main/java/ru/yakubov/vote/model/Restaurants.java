package ru.yakubov.vote.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = Restaurants.ALL_SORTED, query = "SELECT m FROM Restaurants m WHERE m.id=:restorauntId ORDER BY m.name DESC"),
        @NamedQuery(name = Restaurants.DELETE, query = "DELETE FROM Restaurants m WHERE m.id=:restorauntId")})
@Entity
@Table(name = "restaurants")
public class Restaurants extends AbstractNamedEntity{
    public static final String ALL_SORTED = "Restaurants.getAll";
    public static final String DELETE = "Restaurants.delete";

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    private String address;

    public Restaurants() {
    }

    public Restaurants(Restaurants r) {
        this(r.getId(), r.getName(), r.getAddress());
    }

    public Restaurants(Integer id, String name, @NotBlank @Size(min = 1, max = 100) String address) {
        super(id, name);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
