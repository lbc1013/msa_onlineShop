package com.example.userMicroservice.domain;

import lombok.Data;

@Data
public class UserDomain {
    private String name;
    private String email;
    private String password;
}