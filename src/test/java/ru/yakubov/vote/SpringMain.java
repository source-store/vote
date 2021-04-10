package ru.yakubov.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.controller.Restaurant.RestaurantRestController;
import ru.yakubov.vote.controller.user.AdminRestController;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.model.User;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println();
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            List<User> user = adminUserController.getAll();
            System.out.println();

            RestaurantRestController restaurantController = appCtx.getBean(RestaurantRestController.class);
            List<Restaurants> restaurants = restaurantController.getAll();
            System.out.println();

        }
    }
}
