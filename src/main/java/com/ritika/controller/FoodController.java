package com.ritika.controller;

import com.ritika.model.Food;
import com.ritika.model.User;
import com.ritika.service.FoodService;
import com.ritika.service.RestaurantService;
import com.ritika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/search")
    public ResponseEntity<List<Food>>searchFood (@RequestParam String name,
                                                 @RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.searchFood(name);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood (
            @RequestParam (required = false, defaultValue = "false") boolean vegetarian,
            @RequestParam (required = false, defaultValue = "false") boolean seasonal,
            @RequestParam (required = false, defaultValue = "false")boolean non_veg,
            @RequestParam(required = false) String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userService.findUserByJwtToken(jwt);

        List<Food> food = foodService.getRestaurantFood(restaurantId, vegetarian, seasonal, non_veg, food_category);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/restaurant/all_food/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantAllFoods(
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) Boolean nonveg,
            @RequestParam(required = false) Boolean sessional,
            @RequestParam(required = false) String foodCategory,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Food> food = foodService.getRestaurantFood(restaurantId, vegetarian, nonveg, sessional, foodCategory);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
