package ru.yakubov.vote.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.web.Restaurant.RestaurantController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;


public class RestaurantServlet extends HttpServlet {
    private static final Logger log = getLogger(RestaurantServlet.class);

    private ConfigurableApplicationContext springContext;
    private RestaurantController restaurantController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        restaurantController = springContext.getBean(RestaurantController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("restaurantId"));
        SecurityUtil.setAuthUserId(userId);
        response.sendRedirect("restaurants");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to restaurants");
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                restaurantController.delete(id);
                response.sendRedirect("restaurants");
            }
            case "menu" -> {
                request.getRequestDispatcher("/menu.jsp").forward(request, response);
                break;
            }
            case "create", "update" -> {
                final Restaurants restaurants = "create".equals(action) ? new Restaurants( null, "name", "address")
                                                                        : restaurantController.get(getId(request));
                request.setAttribute("restaurants", restaurants);
                request.getRequestDispatcher("/restaurantForm.jsp").forward(request, response);
            }
//            case "filter" -> {
//                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
//                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
//                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
//                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
//                request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
//            }
            default -> {
                request.setAttribute("restaurants", restaurantController.getAll());
                request.getRequestDispatcher("/restaurants.jsp").forward(request, response);
                break;
            }
        }
//        request.getRequestDispatcher("/restaurants.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
