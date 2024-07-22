package com.ritika.controller;

import com.ritika.model.IngredientsCategory;
import com.ritika.model.IngredientsItem;
import com.ritika.request.IngredientCategoryRequest;
import com.ritika.request.IngredientRequest;
import com.ritika.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientsCategory(
            @RequestBody IngredientCategoryRequest req) throws Exception{

        IngredientsCategory ingredientsCategory = ingredientsService.createIngredientsCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(ingredientsCategory, HttpStatus.CREATED);
     }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientsItem(
            @RequestBody IngredientRequest req) throws Exception{

        IngredientsItem ingredientItem = ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id) throws Exception{

        IngredientsItem ingredientItem = ingredientsService.updateStock(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(
            @PathVariable Long id) throws Exception{

        List<IngredientsItem> ingredientItem = ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientsCategory(
            @PathVariable Long id) throws Exception{

        List<IngredientsCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
