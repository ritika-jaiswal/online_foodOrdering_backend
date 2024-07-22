package com.ritika.request;

import com.ritika.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address diliveryAddress;
}
