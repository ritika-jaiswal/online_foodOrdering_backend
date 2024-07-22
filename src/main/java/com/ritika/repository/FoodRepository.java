package com.ritika.repository;

import com.ritika.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

//    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId")
    List<Food> findFoodsByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("select f from Food f where f.name like %:keyword% or f.foodCategory.name like %:keyword%")
    List<Food>searchFood(@Param("keyword") String keyword);

    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId " +
            "AND (:isVegetarian IS NULL OR f.isVegetarian = COALESCE(:isVegetarian, f.isVegetarian)) " +
            "AND (:isNonveg IS NULL OR f.isVegetarian = COALESCE(:isNonveg, f.isVegetarian)) " +
            "AND (:isSeasonal IS NULL OR f.isSeasonal = COALESCE(:isSeasonal, f.isSeasonal)) " +
            "AND (:foodCategory IS NULL OR f.foodCategory.name = :foodCategory)")
    List<Food> findFilteredFoods(@Param("restaurantId") Long restaurantId,
                                 @Param("isVegetarian") Boolean isVegetarian,
                                 @Param("isNonveg") Boolean isNonveg,
                                 @Param("isSeasonal") Boolean isSeasonal,
                                 @Param("foodCategory") String foodCategory);

}
