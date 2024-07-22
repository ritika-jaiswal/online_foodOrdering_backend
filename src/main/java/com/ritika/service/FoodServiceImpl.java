package com.ritika.service;

import com.ritika.model.Category;
import com.ritika.model.Food;
import com.ritika.model.Restaurant;
import com.ritika.repository.FoodRepository;
import com.ritika.repository.RestaurantRepository;
import com.ritika.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Food createdFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredientsItems(req.getIngredients());
        food.setSeasonal(req.isSessional());
        food.setVegetarian(req.isVegetarian());
        food.setCreationDate(new Date(System.currentTimeMillis()));

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSessional,
                                        String foodCategory) {

        List<Food> foods = foodRepository.findFoodsByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }

        if (isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }

        if (isSessional) {
            foods = filterBySessional(foods, isSessional);
        }

        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySessional(List<Food> foods, boolean isSessional) {
        return foods.stream().filter(food -> food.isSeasonal() == isSessional).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> getAllFoodByRestaurantId(Long restaurantId,
                                               Boolean isVegetarian,
                                               Boolean isNonveg,
                                               Boolean isSeasonal,
                                               String foodCategory) {
        return foodRepository.findFilteredFoods(restaurantId, isVegetarian, isNonveg, isSeasonal, foodCategory);
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if(optionalFood.isEmpty()){
            throw new Exception("food not exist..");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
