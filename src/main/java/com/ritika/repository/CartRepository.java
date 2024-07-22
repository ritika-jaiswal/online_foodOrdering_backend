package com.ritika.repository;

import com.ritika.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByCustomerId(Long userId);
}
