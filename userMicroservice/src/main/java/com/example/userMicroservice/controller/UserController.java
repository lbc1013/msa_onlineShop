package com.example.userMicroservice.controller;

import com.example.userMicroservice.jpa.UserEntity;
import com.example.userMicroservice.vo.ResponseUser;
import com.example.userMicroservice.vo.UserDomain;
import com.example.userMicroservice.dto.UserDto;
import com.example.userMicroservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
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

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUser> getUsers(@PathVariable("userId") String userId) {
        UserDto user = userService.getUserById(userId);
        ResponseUser result = new ModelMapper().map(user, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
