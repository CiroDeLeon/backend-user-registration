package com.cirodeleon.userregistration.controller;


import com.cirodeleon.userregistration.dto.UserDto;
import com.cirodeleon.userregistration.dto.UserResponseDto;
import com.cirodeleon.userregistration.entity.User;
import com.cirodeleon.userregistration.exception.EmailAlreadyRegisteredException;
import com.cirodeleon.userregistration.service.UserService;
import jakarta.validation.Valid;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json")
public class UserController {

    private final UserService userService;

    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.convertToEntity(userDto);
        User registeredUser = userService.registerUser(user);
        UserResponseDto userResponse = userService.convertToResponseDto(registeredUser);
        return ResponseEntity.ok(userResponse);
    }
    
    
    
    
    
    
    
    
    
    

}
