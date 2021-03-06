package com.example.useroperations.service;

import com.example.useroperations.dto.UserRequest;
import com.example.useroperations.exception.UserEmailAlreadyExistsException;
import com.example.useroperations.exception.UserIdAlreadyExistsException;
import com.example.useroperations.exception.UserNameAlreadyExistsException;
import com.example.useroperations.exception.UserNotFoundException;
import com.example.useroperations.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.useroperations.repository.UserRepository;
import java.util.Optional;

@Service
@Log4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   // private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    // ModelMapper: Bir nesne modelinin Dto adındaki nesne modeliyle eşlenmesi için kullanılır.
    private final ModelMapper modelMapper;

    private final UserMapperService userMapperService;


    @Override
    public UserEntity getUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public UserEntity createUser(UserRequest userRequest) {

        // Optional: NullPointerException hatasını önlemek için kullanılır.
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userRequest.getId());

        // .isPresent : Mevcut bir deger varsa true yoksa false döndürür
        if (optionalUserEntity.isPresent()) {
            throw new UserNotFoundException();
        }
        if (userRepository.getByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException();
        }

        if (userRepository.getByUserName(userRequest.getUserName()).isPresent()) {
            throw new UserEmailAlreadyExistsException();
        }

        //UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
        UserEntity userEntity1 = userMapperService.toDto(userRequest);
        //PropertyConfigurator.configure("src/main/resources/log4j.properties");
        log.info("Kullanıcı oluşturuldu.");
        return userRepository.save(userEntity1);
    }

    @Override
    public UserEntity updateUser(int id, UserRequest userRequest) {

        UserEntity newUser = userRepository.findById(id).orElseThrow(UserIdAlreadyExistsException::new);

        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setYearOfBirth(userRequest.getYearOfBirth());

        Optional<UserEntity> optinalUser=userRepository.getByEmail(userRequest.getEmail());
        if(newUser.getEmail() != userRequest.getEmail() && optinalUser.isPresent() ) {
            throw new UserEmailAlreadyExistsException();
        }

        optinalUser=userRepository.getByUserName(userRequest.getUserName());
        if(newUser.getUserName() != userRequest.getUserName() && optinalUser.isPresent() ) {
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
