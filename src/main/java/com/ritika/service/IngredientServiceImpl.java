package com.ritika.service;

import com.ritika.model.IngredientsCategory;
import com.ritika.model.IngredientsItem;
import com.ritika.model.Restaurant;
import com.ritika.repository.IngrediantsItemRepository;
import com.ritika.repository.IngredientCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngrediantsItemRepository ingrediantsItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory category = new IngredientsCategory();
        category.setName(name);
        category.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(category);

    }

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> optional = ingredientCategoryRepository.findById(id);

        if(optional.isEmpty()){
            throw new Exception("Ingredient category not found");
        }
        return optional.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientCategory = findIngredientsCategoryById(categoryId);

        IngredientsItem ingredientItem = new IngredientsItem();
        ingredientItem.setRestaurant(restaurant);
        ingredientItem.setName(ingredientName);
        ingredientItem.setCategory(ingredientCategory);

        IngredientsItem ingredientsItem = ingrediantsItemRepository.save(ingredientItem);
        ingredientCategory.getIngredients().add(ingredientsItem);

        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantID) {
        return ingrediantsItemRepository.findByRestaurantId(restaurantID);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingrediantsItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient item not found");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingrediantsItemRepository.save(ingredientsItem);
    }
}
