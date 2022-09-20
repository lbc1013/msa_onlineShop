package com.example.orderservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RequestOrder {
    private String productId;
    private String qty;
    private Integer unitPrice;
}
