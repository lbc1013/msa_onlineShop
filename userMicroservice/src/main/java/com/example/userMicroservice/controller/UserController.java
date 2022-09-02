package com.example.userMicroservice.controller;

import com.example.userMicroservice.ResponseUser;
import com.example.userMicroservice.domain.UserDomain;
import com.example.userMicroservice.dto.UserDto;
import com.example.userMicroservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user-service")
public class UserController {
    private Environment env;
    private UserService userService;


    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String getStatus() {
        return String.format("It's working well on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseUser> createUser(@RequestBody UserDomain userDomain) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userDomain, UserDto.class);
        Date date = new Date(System.currentTimeMillis());
        userDto.setCreateAt(date);
        userService.CreateUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
