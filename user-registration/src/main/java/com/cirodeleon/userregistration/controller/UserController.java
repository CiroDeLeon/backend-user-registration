package com.cirodeleon.userregistration.controller;


import com.cirodeleon.userregistration.dto.LoginDto;
import com.cirodeleon.userregistration.dto.UserDto;
import com.cirodeleon.userregistration.dto.UserResponseDto;
import com.cirodeleon.userregistration.entity.User;
import com.cirodeleon.userregistration.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private final UserService userService;

    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.convertToEntity(userDto);
        User registeredUser = userService.registerUser(user);
        UserResponseDto userResponse = userService.convertToResponseDto(registeredUser);
        return ResponseEntity.ok(userResponse);
    }
    
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<UserResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto.getEmail(), loginDto.getPassword());
        UserResponseDto userResponse = userService.convertToResponseDto(user);
        return ResponseEntity.ok(userResponse);
    }
    
    @GetMapping("/all")
    @Operation(security = { @SecurityRequirement(name = "BearerAuth") })
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
}
