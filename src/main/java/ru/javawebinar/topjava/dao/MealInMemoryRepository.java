package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealInMemoryRepository implements MealRepository{
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(MealInMemoryRepository.class);
    private final List<Meal> meals;

    public MealInMemoryRepository() {
        meals = new CopyOnWriteArrayList<>(new Meal[]{
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        });
    }

    @Override
    public void delete(Integer mealId) {
        meals.removeIf(meal -> Objects.equals(meal.getId(), mealId));
    }

    @Override
    public void add(Meal meal) {
        meal.setId(count.incrementAndGet());
        meals.add(meal);
    }

    @Override
    public void update(Meal meal) {
        log.info("Meal updated: {}", getById(meal.getId()));
        log.info("Meal updated: {}", meals.indexOf(meal));
        Meal newMeal = getById(meal.getId());

        int count = 0;
        for (Meal m : meals) {
            if(m.getId().equals(newMeal.getId())) {break;}
            count++;
        }
        newMeal.setCalories(meal.getCalories());
        newMeal.setDescription(meal.getDescription());
        newMeal.setDateTime(meal.getDateTime());
        meals.set(count, newMeal);
    }

    @Override
    public Meal getById(Integer mealId) {
        return meals.stream().filter(meal -> meal.getId().equals(mealId)).findFirst().orElseGet(null);
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    public static int incrementCounter() {
        return count.incrementAndGet();
    }
}
