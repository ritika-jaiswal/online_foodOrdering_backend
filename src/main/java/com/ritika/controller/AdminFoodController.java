package com.ritika.controller;

import com.ritika.model.Food;
import com.ritika.model.Restaurant;
import com.ritika.model.User;
import com.ritika.request.CreateFoodRequest;
import com.ritika.response.MessageResponse;
import com.ritika.service.FoodService;
import com.ritika.service.RestaurantService;
import com.ritika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createdFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userService.findUserByJwtToken(jwt);
         foodService.deleteFood(id);

         MessageResponse res = new MessageResponse();
         res.setMessage("Successfully deleted food");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws  Exception{

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

}
