package com.example.useroperations.service;

import com.example.useroperations.dto.UserCreatRequest;
import com.example.useroperations.dto.UserUpdateRequest;
import com.example.useroperations.exception.UserEmailAlreadyExistsException;
import com.example.useroperations.exception.UserIdAlreadyExistsException;
import com.example.useroperations.exception.UserNameAlreadyExistsException;
import com.example.useroperations.exception.UserNotFoundException;
import com.example.useroperations.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.useroperations.repository.UserRepository;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    // ModelMapper: Bir nesne modelinin Dto adındaki nesne modeliyle eşlenmesi için kullanılır.
    private ModelMapper modelMapper;

    @Autowired // UserRepository'ye karşılık gelen sınıfı arıyor
    // Constructor, degişken, setter metodlar için dependeny injection işlemini yapar
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity getUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public UserEntity createUser(UserCreatRequest userCreatRequest) {

        // Optional: NullPointerException hatasını önlemek için kullanılır.
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userCreatRequest.getId());

        // .isPresent : Mevcut bir deger varsa true yoksa false döndürür
        if (optionalUserEntity.isPresent()) {
            throw new UserNotFoundException();
        }
        if (userRepository.getByEmail(userCreatRequest.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException();
        }

        if (userRepository.getByUserName(userCreatRequest.getUserName()).isPresent()) {
            throw new UserEmailAlreadyExistsException();
        }

        UserEntity userEntity = modelMapper.map(userCreatRequest, UserEntity.class);

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUser(int id, UserUpdateRequest userUpdateRequest) {

        UserEntity newUser = userRepository.findById(id).orElseThrow(UserIdAlreadyExistsException::new);

        newUser.setFirstName(userUpdateRequest.getFirstName());
        newUser.setLastName(userUpdateRequest.getLastName());
        newUser.setYearOfBirth(userUpdateRequest.getYearOfBirth());

        Optional<UserEntity> optinalUser=userRepository.getByEmail(userUpdateRequest.getEmail());
        if(newUser.getEmail() != userUpdateRequest.getEmail() && optinalUser.isPresent() ) {
            throw new UserEmailAlreadyExistsException();
        }

        optinalUser=userRepository.getByUserName(userUpdateRequest.getUserName());
        if(newUser.getUserName() != userUpdateRequest.getUserName() && optinalUser.isPresent() ) {
            throw new UserNameAlreadyExistsException();
        }

        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userEntity);
    }
}
