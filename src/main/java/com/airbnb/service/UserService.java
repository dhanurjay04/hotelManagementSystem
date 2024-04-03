package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
@Autowired
    private JWTService jwtService;
    @Autowired
    private PropertyUserRepository userRepository;


    public UserService(JWTService jwtService, PropertyUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    public PropertyUser addUser(PropertyUserDto dto){

        PropertyUser user= new PropertyUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setUserRole(dto.getUserRole());
        user.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        userRepository.save(user);
        return user;

    }


    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> OpUser = userRepository.findByUsername(loginDto.getUsername());
     if(OpUser.isPresent()){
         PropertyUser user = OpUser.get();
         if( BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){

            return   jwtService.generateToken(user);

         }
      }
     return null;
    }
}
