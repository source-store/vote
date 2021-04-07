package ru.yakubov.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

//            AdminVoteRestController adminUserController = appCtx.getBean(AdminVoteRestController.class);
//            System.out.println();
//            User user = adminUserController.create(new User(UserTestData.newUser));
//            System.out.println();

//            RestaurantRestController restaurantController = appCtx.getBean(RestaurantRestController.class);
//            Restaurants restaurants = restaurantController.create(new Restaurants(RestaurantTestData.new_restaurant));
//            System.out.println();

//            AdminMenuRestController menuRestController = appCtx.getBean(AdminMenuRestController.class);
//            Menu menu = new Menu(MenuTestData.NEW_MENU);
//            menu.setRestaurant(restaurants);
//            menuRestController.create(menu);
//            System.out.println();

//            ProfileVoteRestController profileVoteRestController = appCtx.getBean(ProfileVoteRestController.class);
//            Votes votes = new Votes();
//            votes.setUser(user);
//            votes.setRestaurant(restaurants);
//            profileVoteRestController.createVote(user.getId(), restaurants.getId());

        }
    }
}
