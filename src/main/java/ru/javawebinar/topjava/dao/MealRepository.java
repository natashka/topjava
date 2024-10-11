package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void add(Meal meal);

    void update(Meal meal);

    Meal getById(Integer id);

    List<Meal> getAll();

    void delete(Integer id);
}
