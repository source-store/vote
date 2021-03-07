package ru.yakubov.vote.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.web.menu.MenuController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MenuVoteServlet extends HttpServlet {
    private static final Logger log = getLogger(MenuVoteServlet.class);

    private ConfigurableApplicationContext springContext;
    private MenuController menuController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        menuController = springContext.getBean(MenuController.class);
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
        response.sendRedirect("menu");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to menu");
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete" -> {
                int menuid = getId(request, "menuid");
                menuController.delete(menuid);
                int restaurantId = getId(request, "id");
                request.setAttribute("menus", menuController.getAllByRestaurantId(restaurantId));
                request.getRequestDispatcher("/menu.jsp").forward(request, response);
                break;
            }
            default -> {
                int restaurantId = getId(request, "id");
                request.setAttribute("menus", menuController.getAllByRestaurantId(restaurantId));
                request.getRequestDispatcher("/menu.jsp").forward(request, response);
                break;
            }
        }
    }
    private int getId(HttpServletRequest request, String param) {
        String paramId = Objects.requireNonNull(request.getParameter(param));
        return Integer.parseInt(paramId);
    }

}
