package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.MEALS) {
            save(meal, authUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal.getId());
        if (meal.isNew()) {
            Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            repository.put(userId, mealMap);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
