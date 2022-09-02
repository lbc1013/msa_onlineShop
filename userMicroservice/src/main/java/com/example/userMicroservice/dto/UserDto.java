package com.example.userMicroservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String userId;
    private Date createAt;

    private String encryptedPwd;
}
