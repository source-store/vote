package ru.yakubov.vote.util;

import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.to.MenuTo;
import ru.yakubov.vote.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtils {

    private VoteUtils() {
    }

    public static List<MenuTo> getMenuTos(Collection<Menu> menu) {
        return menu.stream()
                .map(mnu -> createTo(mnu))
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> getRestaurantTos(Collection<Restaurants> restaurants) {
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant))
                .collect(Collectors.toList());
    }
    public static RestaurantTo createTo(Restaurants restaurants) {
        return new RestaurantTo(restaurants.getId(), restaurants.getName(), restaurants.getAddress());
    }

    public static MenuTo createTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDate(), menu.getDecription(), menu.getPrice(), createTo(menu.getRestaurant()));
    }

}
