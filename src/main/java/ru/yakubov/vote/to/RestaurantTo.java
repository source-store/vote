package ru.yakubov.vote.to;

public class RestaurantTo {

    private final Integer id;

    private final String name;

    private final String address;

    public RestaurantTo(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
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
