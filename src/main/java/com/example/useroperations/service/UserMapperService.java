package com.example.useroperations.service;

import com.example.useroperations.dto.UserRequest;
import com.example.useroperations.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapperService {

    UserEntity toDto(UserRequest userRequest);
}
