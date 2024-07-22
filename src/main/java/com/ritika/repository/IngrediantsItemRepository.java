package com.ritika.repository;

import com.ritika.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngrediantsItemRepository extends JpaRepository<IngredientsItem, Long> {

    List<IngredientsItem> findByRestaurantId(Long id);
}
