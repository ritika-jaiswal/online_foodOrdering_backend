package com.ritika.request;

import com.ritika.model.Category;
import com.ritika.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean sessional;

    private List<IngredientsItem> ingredients;

}
