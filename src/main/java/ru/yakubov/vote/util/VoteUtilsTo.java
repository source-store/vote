package ru.yakubov.vote.util;

import ru.yakubov.vote.model.*;
import ru.yakubov.vote.to.MenuTo;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.to.UserTo;
import ru.yakubov.vote.to.VoteTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtilsTo {

    private VoteUtilsTo() {
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
        return new MenuTo(menu.getId(), menu.getDate(), menu.getDescription(), menu.getPrice(), createTo(menu.getRestaurant()));
    }

    public static UserTo createTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static VoteTo createTo(Votes votes) {
        return (votes == null) ? null : new VoteTo(votes.getId(), votes.getDate(), votes.getUser().getId(), votes.getRestaurant().getId());
    }

    public static User createUserFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), Role.USER);
    }
}
