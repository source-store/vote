package ru.yakubov.vote;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.model.Role;
import ru.yakubov.vote.model.UserVote;
import ru.yakubov.vote.to.RestaurantTo;
import ru.yakubov.vote.web.Restaurant.RestaurantController;
import ru.yakubov.vote.web.user.AdminVoteRestController;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminVoteRestController adminUserController = appCtx.getBean(AdminVoteRestController.class);
            adminUserController.create(new UserVote(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println();

            RestaurantController restaurantController = appCtx.getBean(RestaurantController.class);
            List<RestaurantTo> filteredMealsWithExcess =
                    restaurantController.getAll();
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();
        }
    }
}
