package com.example.userMicroservice;

import lombok.Data;

@Data
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
