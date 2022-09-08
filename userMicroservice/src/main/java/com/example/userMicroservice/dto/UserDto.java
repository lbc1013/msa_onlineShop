package com.example.userMicroservice.dto;

import com.example.userMicroservice.vo.ResponseOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String userId;
    private Date createAt;

    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
