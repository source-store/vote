package ru.yakubov.vote.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.yakubov.vote.model.Menu;
import ru.yakubov.vote.model.Restaurants;
import ru.yakubov.vote.to.MenuTo;
import ru.yakubov.vote.web.menu.MenuController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
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
        request.setCharacterEncoding("UTF-8");

        if (StringUtils.hasLength(request.getParameter("menuid"))) {
            int menuid = getId(request, "menuid");
            int id = menuController.getTo(menuid).getRestaurant().getId();
            Menu menu = new Menu(null, request.getParameter("description"), LocalDate.parse(request.getParameter("date")),
                    new Restaurants(), Long.parseLong(request.getParameter("price")));
            menu.getRestaurant().setId(id);
            menuController.update(menu, menuid);
            request.setAttribute("menus", menuController.getAllByRestaurantId(id));
            request.getRequestDispatcher("/menu.jsp").forward(request, response);
        }
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
            case "create" -> {
                final LocalDate localDate = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                final MenuTo menu = new MenuTo(null, localDate, "menu", 0L, null);
                request.setAttribute("id", menu);
                request.setAttribute("menu", menu);
                request.getRequestDispatcher("/menuForm.jsp").forward(request, response);
                break;
            }
            case "update" -> {
                int menuid = getId(request, "menuid");
                final MenuTo menu = menuController.getTo(menuid);
                request.setAttribute("id", menu.getRestaurant().getId());
                request.setAttribute("menu", menu);
                request.getRequestDispatcher("/menuForm.jsp").forward(request, response);
                break;
            }
            default -> {
                int restaurantId = getId(request, "id");
//                request.setAttribute("id", restaurantId);
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
