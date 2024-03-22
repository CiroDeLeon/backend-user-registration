package com.cirodeleon.userregistration.service;

import com.cirodeleon.userregistration.dto.PhoneDto;
import com.cirodeleon.userregistration.dto.UserDto;
import com.cirodeleon.userregistration.dto.UserResponseDto;
import com.cirodeleon.userregistration.entity.Phone;
import com.cirodeleon.userregistration.entity.User;
import com.cirodeleon.userregistration.exception.EmailAlreadyRegisteredException;
import com.cirodeleon.userregistration.repository.UserRepository;
import com.cirodeleon.userregistration.utils.JwtUtil;
import com.cirodeleon.userregistration.utils.PasswordUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    public UserService(UserRepository userRepository,JwtUtil jwtUtil,PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.jwtUtil=jwtUtil;
        this.passwordUtil=passwordUtil;
    }
    @Transactional 
    public User registerUser(User user) throws EmailAlreadyRegisteredException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
        	throw new EmailAlreadyRegisteredException("El correo ya registrado");
        }
        user.setCreated(new Date());
        user.setModified(new Date());
        user.setLastLogin(new Date());
        user.setActive(true);
        user.setToken(jwtUtil.generateToken(user));
        user.setPassword(passwordUtil.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }
    
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // Considera encriptar la contraseña aquí
        user.setPhones(convertPhoneDtosToEntities(userDto.getPhones(), user));
        // Otros campos necesarios
        return user;
    }
    
    public Set<Phone> convertPhoneDtosToEntities(Set<PhoneDto> phoneDtos, User user) {
        return phoneDtos.stream().map(phoneDto -> {
            Phone phone = new Phone();
            phone.setNumber(phoneDto.getNumber());
            phone.setCitycode(phoneDto.getCitycode());
            phone.setCountrycode(phoneDto.getCountrycode());
            phone.setUser(user); 
            return phone;
        }).collect(Collectors.toSet());
    }
    
    public UserResponseDto convertToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreated(user.getCreated());
        dto.setModified(user.getModified());
        dto.setLastLogin(user.getLastLogin());
        dto.setToken(user.getToken());
        dto.setActive(user.isActive());
        return dto;
    }
}
