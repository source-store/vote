package ru.yakubov.vote;

import ru.yakubov.vote.model.Restaurants;

import java.util.ArrayList;
import java.util.List;

import static ru.yakubov.vote.UserTestData.USER_ID3;

public class RestaurantTestData {
    public static TestMatcher<Restaurants> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator("votes", "menu");

    public static final int RESTAURANT_TEST_SEQ = USER_ID3;

    public static final int RESTAURANT_ID1 = RESTAURANT_TEST_SEQ + 1;
    public static final int RESTAURANT_ID2 = RESTAURANT_TEST_SEQ + 2;
    public static final int RESTAURANT_ID3 = RESTAURANT_TEST_SEQ + 3;
    public static final int RESTAURANT_ID4 = RESTAURANT_TEST_SEQ + 4;

    public static final Restaurants new_restaurant = new Restaurants(null, "new_Ресторан", "new_адрес");
    public static final Restaurants restaurant1 = new Restaurants(RESTAURANT_ID1, "Ресторан1", "адрес1");
    public static final Restaurants restaurant2 = new Restaurants(RESTAURANT_ID2, "Ресторан2", "адрес2");
    public static final Restaurants restaurant3 = new Restaurants(RESTAURANT_ID3, "Ресторан3", "адрес3");
    public static final Restaurants restaurant4 = new Restaurants(RESTAURANT_ID4, "Ресторан4", "адрес4");

    public static final List<Restaurants> restaurants = new ArrayList<>();

    static {
        restaurant1.setVotes(VoteTestData.VOTES1);
        restaurant2.setVotes(VoteTestData.VOTES2);

        restaurant1.setMenu(MenuTestData.MENUS1);
        restaurant2.setMenu(MenuTestData.MENUS2);

        restaurant3.setMenu(MenuTestData.MENUS3);
        restaurant4.setMenu(MenuTestData.MENUS4);

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
    }


}
