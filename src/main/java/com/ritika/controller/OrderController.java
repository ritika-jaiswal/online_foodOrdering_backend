package com.ritika.controller;

import com.ritika.model.CartItem;
import com.ritika.model.Order;
import com.ritika.model.User;
import com.ritika.request.AddCartItemRequest;
import com.ritika.request.OrderRequest;
import com.ritika.service.OrderService;
import com.ritika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }
}

