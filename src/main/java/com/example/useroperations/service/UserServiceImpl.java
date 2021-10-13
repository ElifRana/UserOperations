package com.example.useroperations.service;

import com.example.useroperations.dto.UserCreatRequest;
import com.example.useroperations.dto.UserUpdateRequest;
import com.example.useroperations.exception.UserEmailAlreadyExistsException;
import com.example.useroperations.exception.UserIdAlreadyExistsException;
import com.example.useroperations.exception.UserNameAlreadyExistsException;
import com.example.useroperations.exception.UserNotFoundException;
import com.example.useroperations.model.UserEntity;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.useroperations.repository.UserRepository;
import java.util.Optional;

@Service
@Log4j
public class UserServiceImpl implements UserService {

   // private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
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
        //PropertyConfigurator.configure("src/main/resources/log4j.properties");
        log.info("Kullanıcı oluşturuldu.");
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
       // PropertyConfigurator.configure("src/main/resources/log4j.properties");
        log.info("Kullanıcı güncellendi");
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userEntity);
       // PropertyConfigurator.configure("src/main/resources/log4j.properties");
        log.info("Kullanıcı silindii");
    }
}
