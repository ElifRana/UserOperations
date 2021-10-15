package com.example.useroperations.controller.rest;

import com.example.useroperations.dto.UserRequest;
import com.example.useroperations.model.UserEntity;
import com.example.useroperations.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public UserEntity getUser(@ApiParam(type = "int", value = "Existing person ID", required = true)@PathVariable int id) {//@pathVariable
        return userService.getUser(id);
    }

    @PostMapping("/user")
    public UserEntity createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/user/{id}")
    public UserEntity updateUser(@PathVariable("id") int id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }
}
