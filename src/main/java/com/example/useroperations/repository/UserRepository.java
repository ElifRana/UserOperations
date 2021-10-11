package com.example.useroperations.repository;

import com.example.useroperations.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity>  getByUserName(String userName);
    //userEntity neden dönemiyoruz

    Optional<UserEntity> getByEmail(String email);
}
