package com.example.useroperations.service;

import com.example.useroperations.dto.UserCreatRequest;
import com.example.useroperations.dto.UserUpdateRequest;
import com.example.useroperations.model.UserEntity;

public interface UserService {

    UserEntity getUser(int id);

    UserEntity createUser(UserCreatRequest userCreatRequestDto);

    UserEntity updateUser(int id, UserUpdateRequest userUpdateRequest);

    void deleteUser(int id);
}
