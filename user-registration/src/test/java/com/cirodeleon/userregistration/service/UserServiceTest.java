package com.cirodeleon.userregistration.service;


import com.cirodeleon.userregistration.entity.Phone;
import com.cirodeleon.userregistration.entity.User;
import com.cirodeleon.userregistration.repository.UserRepository;
import com.cirodeleon.userregistration.utils.JwtUtil;
import com.cirodeleon.userregistration.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtil passwordUtil;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("SecurePassword123");
        
        
        Set<Phone> phones = new HashSet<>();
        Phone phone = new Phone();
        phone.setNumber("123456789");
        phone.setCitycode("1");
        phone.setCountrycode("57");
        phones.add(phone);
        user.setPhones(phones);
    }

    @Test
    void registerUserShouldReturnUserWithPhonesWhenSuccessful() {
        // Configuración del mock para simular que el usuario no existe previamente.
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Simulando el comportamiento de guardar el usuario y que este sea retornado al hacerlo.
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Simulando el comportamiento del PasswordUtil y JwtUtil
        when(passwordUtil.hashPassword(anyString())).thenReturn("hashedPassword");
        when(jwtUtil.generateToken(any(User.class))).thenReturn("dummyToken");

        // Ejecución del método bajo prueba y aserción para comprobar que no lanza una excepción.
        assertDoesNotThrow(() -> {
            User registeredUser = userService.registerUser(user);
            
            // Verificaciones
            // Verificar que el usuario registrado no sea nulo.
            assertThat(registeredUser).isNotNull();

            // Verificar que el usuario tiene exactamente 1 teléfono registrado y los detalles son correctos.
            assertThat(registeredUser.getPhones()).hasSize(1);
            Phone registeredPhone = registeredUser.getPhones().iterator().next();
            assertThat(registeredPhone.getNumber()).isEqualTo("123456789");
            assertThat(registeredPhone.getCitycode()).isEqualTo("1");
            assertThat(registeredPhone.getCountrycode()).isEqualTo("57");

            // Verificar las interacciones con los mocks.
            verify(userRepository).findByEmail(anyString());
            verify(userRepository).save(any(User.class));
            verify(passwordUtil).hashPassword(anyString());
            verify(jwtUtil).generateToken(any(User.class));
        });
    }




    @Test
    void registerUserShouldThrowWhenEmailAlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        
        Exception exception = assertThrows(Exception.class, () -> userService.registerUser(user));
        
        assertThat(exception.getMessage()).contains("El correo ya registrado");
        verify(userRepository, never()).save(any(User.class));
    }
}

