package ru.yakubov.vote.to;

import java.io.Serializable;

public class RestaurantTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = -4783984057724957103L;

    private final String name;

    private final String address;

    public RestaurantTo(Integer id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public RestaurantTo(Integer id) {
        super(id);
        this.name = null;
        this.address = null;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name=" + name +
                ", address=" + address +
                '}';
    }

}
