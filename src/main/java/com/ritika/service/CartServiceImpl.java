package com.ritika.service;

import com.ritika.model.Cart;
import com.ritika.model.CartItem;
import com.ritika.model.Food;
import com.ritika.model.User;
import com.ritika.repository.CartItemRepository;
import com.ritika.repository.CartRepository;
import com.ritika.repository.FoodRepository;
import com.ritika.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;


    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem: cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()){
            throw new Exception("CartItem not found");
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);

        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return  cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()){
            throw new Exception("CartItem not found");
        }

        CartItem item = cartItem.get();
        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total = 0L;
        for(CartItem cartItem: cart.getItem()){
            total += cartItem.getFood().getPrice()*cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isEmpty()){
            throw new Exception("CartItem not found with id: " + id);
        }
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));

        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();

        return cartRepository.save(cart);
    }
}
