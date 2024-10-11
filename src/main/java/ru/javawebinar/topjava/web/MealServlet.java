package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.MealInMemoryRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String MEALS = "/meals.jsp";
    private static final String ADD = "/addMeal.jsp";
    private static final String UPDATE = "/editMeal.jsp";
    private static final int CALORIES_PER_DAY = 2005;
    private final MealRepository mealRepository = new MealInMemoryRepository();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String forward;
        log.info("action: {}", action);
        if (action == null) {
            action = "mealList";
        }
        if (action.equals("delete")) {
            mealRepository.delete(Integer.parseInt(request.getParameter("id")));
            forward = MEALS;
        } else {
            if (action.equalsIgnoreCase("add")) {
                forward = ADD;
                request.setAttribute("meal", new Meal());
                log.info("add meal");
            } else if (action.equalsIgnoreCase("update")) {
                forward = UPDATE;
                int mealId = Integer.parseInt(request.getParameter("id"));
                Meal meal = mealRepository.getById(mealId);
                request.setAttribute("meal", meal);
                log.info("Updating form for meal with id {}", meal.getId());
            } else {
                forward = MEALS;
                List<MealTo> meals = transfer(mealRepository.getAll());
                request.setAttribute("meals", meals);
                log.info("Getting meals list");
            }
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty() || Integer.parseInt(mealId) == 0) {
            log.info("ADDING: In POST id is null or empty {}", mealId);
            mealRepository.add(meal);
        } else {
            log.info("UPDATING: In POST id is {}", mealId);
            meal.setId(Integer.parseInt(mealId));
            mealRepository.update(meal);
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }

    private List<MealTo> transfer(List<Meal> meals) {
        return filteredByStreams(meals,
                LocalTime.MIN,
                LocalTime.MAX,
                CALORIES_PER_DAY);
    }
}
