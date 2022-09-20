package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String productId;
    private String qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private Date createdAt;
}
