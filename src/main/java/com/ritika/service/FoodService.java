package com.ritika.service;

import com.ritika.model.Category;
import com.ritika.model.Food;
import com.ritika.model.Restaurant;
import com.ritika.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createdFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegitarian,
                                        boolean isNonveg,
                                        boolean isSessional,
                                        String FoodCategory
                                        );

    public List<Food> getAllFoodByRestaurantId(Long restaurantId,Boolean isVegitarian,
                                               Boolean isNonveg,
                                               Boolean isSessional,
                                               String FoodCategory);

    public List<Food> searchFood(String keyword) ;

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
