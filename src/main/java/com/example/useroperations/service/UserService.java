package com.example.useroperations.service;

import com.example.useroperations.dto.UserRequest;
import com.example.useroperations.model.UserEntity;

public interface UserService {

    UserEntity getUser(int id);

    UserEntity createUser(UserRequest userRequest);

    UserEntity updateUser(int id, UserRequest userRequest);

    void deleteUser(int id);
}
