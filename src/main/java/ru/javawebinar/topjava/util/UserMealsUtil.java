package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        for (UserMealWithExceed item : mealListWithExceed) {
            System.out.println(item.toString());
        }

        System.out.println("----------------------------");
        mealListWithExceed = getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (UserMealWithExceed item : mealListWithExceed) {
            System.out.println(item.toString());
        }
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();
        LocalDate ld;
        for (UserMeal item : mealList) {
            ld = item.getDateTime().toLocalDate();
            mapCaloriesPerDay.put(ld, mapCaloriesPerDay.getOrDefault(ld, 0) + item.getCalories());
        }

        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal item : mealList) {
            if (TimeUtil.isBetween(item.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(item.getDateTime(), item.getDescription(), item.getCalories(), mapCaloriesPerDay.get(item.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return list;
    }

    private static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCaloriesPerDay = mealList.stream().collect(Collectors.groupingBy(item -> item.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(item -> TimeUtil.isBetween(item.getDateTime().toLocalTime(), startTime, endTime))
                .map(item -> new UserMealWithExceed(item.getDateTime(), item.getDescription(), item.getCalories(), mapCaloriesPerDay.get(item.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
