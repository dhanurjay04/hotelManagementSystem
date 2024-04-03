package com.airbnb.controller;

import ch.qos.logback.core.util.DelayStrategy;
import com.airbnb.dto.JWTResponse;
import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    //@RequestMapping(name="/addUser",method = RequestMethod.POST)//this also one method to
    //write annotation

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //http://localhost:8080/api/v1/users/addUser
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto dto) {

        PropertyUser user = userService.addUser(dto);
        if (user != null) {
            return new ResponseEntity<>("signUp successful", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        String jwtToken = userService.verifyLogin(loginDto);


        if (jwtToken!=null) {
            JWTResponse jwtResponse=  new JWTResponse();
            jwtResponse.setToken(jwtToken);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>("Invalid credential ", HttpStatus.UNAUTHORIZED);
        }
    }
@GetMapping("/profile")
    public PropertyUser getCurrentProfile(@AuthenticationPrincipal  PropertyUser user)  {
        return user;


    }

}

