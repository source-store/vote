package ru.yakubov.vote;

import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.UserVote;

import java.time.LocalDate;
import java.util.List;

import static ru.yakubov.vote.RestaurantTestData.RESTAURANT_ID4;
import static ru.yakubov.vote.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator("restaurant");
    public static final int MENU_TEST_SEQ = RESTAURANT_ID4+1;

    public static final Menu NEW_MENU = new Menu(null, "new_menu", LocalDate.of(2021, 1, 10), 3);
    public static final Menu NEW_MENU1 = new Menu(null, "new_menu1", LocalDate.of(2021, 1, 1), 4);

    public static final Menu MENU1 = new Menu(     MENU_TEST_SEQ, "menu1", LocalDate.of(2021, 3, 10), 50);
    public static final Menu MENU2 = new Menu(MENU_TEST_SEQ+1, "menu2", LocalDate.of(2021, 3, 10), 10);
    public static final Menu MENU3 = new Menu(MENU_TEST_SEQ+2, "menu3", LocalDate.of(2021, 3, 10), 20);

    public static final Menu MENU4 = new Menu(MENU_TEST_SEQ+3, "menu4", LocalDate.of(2021, 3, 10), 30);
    public static final Menu MENU5 = new Menu(MENU_TEST_SEQ+4, "menu5", LocalDate.of(2021, 3, 10), 20);

    public static final Menu MENU6 = new Menu(MENU_TEST_SEQ+5, "menu6", LocalDate.of(2021, 3, 11), 25);
    public static final Menu MENU7 = new Menu(MENU_TEST_SEQ+6, "menu7", LocalDate.of(2021, 3, 11), 15);

    public static final Menu MENU8 = new Menu(MENU_TEST_SEQ+7, "menu8", LocalDate.of(2021, 3, 15), 10);
    public static final Menu MENU9 = new Menu(MENU_TEST_SEQ+8, "menu9", LocalDate.of(2021, 3, 17), 15);

    public static final int MENU_END_SEQ = MENU_TEST_SEQ+8;

    public static final List<Menu> MENUS1 = List.of(MENU1, MENU2, MENU3, MENU6, MENU7);
    public static final List<Menu> MENUS2 = List.of(MENU4, MENU5);
    public static final List<Menu> MENUS3 = List.of(MENU8);
    public static final List<Menu> MENUS4 = List.of(MENU9);


    static {
        NEW_MENU.setRestaurant(RestaurantTestData.restaurant1);
        MENU1.setRestaurant(RestaurantTestData.restaurant1);
        MENU2.setRestaurant(RestaurantTestData.restaurant1);
        MENU3.setRestaurant(RestaurantTestData.restaurant1);

        MENU4.setRestaurant(RestaurantTestData.restaurant2);
        MENU5.setRestaurant(RestaurantTestData.restaurant2);

        MENU6.setRestaurant(RestaurantTestData.restaurant1);
        MENU7.setRestaurant(RestaurantTestData.restaurant1);
        MENU8.setRestaurant(RestaurantTestData.restaurant3);
        MENU9.setRestaurant(RestaurantTestData.restaurant4);

    }


}
