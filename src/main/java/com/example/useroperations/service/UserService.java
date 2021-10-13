package com.example.useroperations.service;

import com.example.useroperations.dto.UserCreatRequest;
import com.example.useroperations.dto.UserUpdateRequest;
import com.example.useroperations.model.UserEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public interface UserService {

    UserEntity getUser(int id);

    UserEntity createUser(UserCreatRequest userCreatRequestDto);

    UserEntity updateUser(int id, UserUpdateRequest userUpdateRequest);

    void deleteUser(int id);
}
