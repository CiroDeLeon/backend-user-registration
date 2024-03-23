package com.cirodeleon.userregistration.controller;



import com.cirodeleon.userregistration.dto.PhoneDto;
import com.cirodeleon.userregistration.dto.UserDto;
import com.cirodeleon.userregistration.entity.User;
import com.cirodeleon.userregistration.exception.EmailAlreadyRegisteredException;
import com.cirodeleon.userregistration.security.JwtRequestFilter;
import com.cirodeleon.userregistration.service.UserService;
import com.cirodeleon.userregistration.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;


import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
	})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @MockBean
    private JwtUtil jwtUtil;
    
    @MockBean
    private SecurityFilterChain securityFilterChain;


    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setEmail("test@dominio.cl");
        userDto.setPassword("10784054P@pa");

       
        Set<PhoneDto> phoneDtos = new HashSet<>();
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber("123456789");
        phoneDto.setCitycode("1");
        phoneDto.setCountrycode("57");
        phoneDtos.add(phoneDto);
        userDto.setPhones(phoneDtos);

        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("SecurePassword123");
    }

    @Test
    void registerUserShouldReturnUserResponseDtoWhenSuccessful() throws Exception {
        when(userService.convertToEntity(any(UserDto.class))).thenReturn(user);
        when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk());
    }
    
    @Test
    void registerUserShouldThrowExceptionWhenEmailAlreadyExists() throws Exception {
        when(userService.convertToEntity(any(UserDto.class))).thenReturn(user);
        when(userService.registerUser(any(User.class))).thenThrow(new EmailAlreadyRegisteredException("El correo ya registrado"));

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isConflict());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
