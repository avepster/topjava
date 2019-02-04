package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> mealListWithExceed = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        for (UserMealWithExceed s : mealListWithExceed) {
            System.out.println(s.getDateTime().toLocalDate() + ": calories = " + s.getCalories() + ", exceed = " + s.getExceed());
        }
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        int calories;
        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal item : mealList) {
            if (TimeUtil.isBetween(item.getDateTime().toLocalTime(), startTime, endTime)) {
                calories = mealList.stream()
                        .filter((s) -> s.getDateTime().toLocalDate().equals(item.getDateTime().toLocalDate()))
                        .mapToInt(UserMeal::getCalories).sum();
                list.add(new UserMealWithExceed(item.getDateTime(), item.getDescription(), calories, calories > caloriesPerDay));
            }
        }
        return list;
    }
}
