package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            ProfileRestController userController = appCtx.getBean(ProfileRestController.class);
            User u = userController.create(new User(null, "user1", "email@mail.ru", "password", Role.USER));
//            userController.update(u, 1);

            MealRestController mealRepo = appCtx.getBean(MealRestController.class);
            Meal userMeal = mealRepo.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            userMeal.setUserId(u.getId());
            Meal adminMeal = mealRepo.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 15, 0), "обед", 500));
            adminMeal.setUserId(adminUserController.getAll().get(0).getId());

            mealRepo.update(adminMeal);
        }
    }
}
