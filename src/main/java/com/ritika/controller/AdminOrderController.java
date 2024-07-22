package com.ritika.controller;

import com.ritika.model.Order;
import com.ritika.model.User;
import com.ritika.request.OrderRequest;
import com.ritika.service.OrderService;
import com.ritika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/rastaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrder(id, order_status);

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
