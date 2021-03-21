package ru.yakubov.vote.util;

import ru.yakubov.vote.model.*;
import ru.yakubov.vote.to.MenuTo;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.to.UserVoteTo;
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
        return new MenuTo(menu.getId(), menu.getDate(), menu.getDecription(), menu.getPrice(), createTo(menu.getRestaurant()));
    }

    public static UserVoteTo createTo(UserVote userVote) {
        return new UserVoteTo(userVote.getId(), userVote.getName(), userVote.getEmail(), userVote.getPassword());
    }

    public static VoteTo createTo(Votes votes) {
        return new VoteTo(votes.getId(), votes.getDate(), votes.getUserVote().getId(), votes.getRestaurant().getId());
    }

    public static UserVote createUserFromTo(UserVoteTo userVoteTo) {
        return new UserVote(null, userVoteTo.getName(), userVoteTo.getEmail(), userVoteTo.getPassword(), Role.USER);
    }
}
