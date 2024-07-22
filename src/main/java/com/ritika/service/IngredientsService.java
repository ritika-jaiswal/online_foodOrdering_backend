package com.ritika.service;

import com.ritika.model.IngredientsCategory;
import com.ritika.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long cayegoryId) throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantID) ;

    public IngredientsItem updateStock(Long id) throws Exception;
}
