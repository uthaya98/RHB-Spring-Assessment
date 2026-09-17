package com.rhb.assignment;
import com.rhb.assignment.dto.LoginRequest;
import com.rhb.assignment.dto.LoginResponse;
import com.rhb.assignment.dto.RegisterRequest;
import com.rhb.assignment.entity.AppUser;
import com.rhb.assignment.repository.UserRepository;

import com.rhb.assignment.service.AuthenticationService;
import com.rhb.assignment.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {

        registerRequest = new RegisterRequest(
                "uthaya",
                "Password123"
        );

        loginRequest = new LoginRequest(
                "uthaya",
                "Password123"
        );
    }

    // --------------------------------------------------
    // REGISTER SUCCESS
    // --------------------------------------------------

    @Test
    void shouldRegisterUserSuccessfully() {

        when(userRepository.existsByUsername("uthaya"))
                .thenReturn(false);

        when(passwordEncoder.encode("Password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(AppUser.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        authenticationService.register(registerRequest);

        verify(userRepository, times(1))
                .existsByUsername("uthaya");

        verify(passwordEncoder, times(1))
                .encode("Password123");

        verify(userRepository, times(1))
                .save(any(AppUser.class));
    }

    // --------------------------------------------------
    // DUPLICATE USER
    // --------------------------------------------------

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {

        when(userRepository.existsByUsername("uthaya"))
                .thenReturn(true);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> authenticationService.register(
                                registerRequest
                        )
                );

        assertEquals(
                "Username already exists",
                exception.getMessage()
        );

        verify(userRepository, never())
                .save(any(AppUser.class));

        verify(passwordEncoder, never())
                .encode(anyString());
    }

    // --------------------------------------------------
    // LOGIN SUCCESS
    // --------------------------------------------------

    @Test
    void shouldLoginSuccessfullyAndReturnJwt() {

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenReturn(authentication);

        when(jwtService.generateToken(authentication))
                .thenReturn("test-jwt-token");

        LoginResponse response =
                authenticationService.login(loginRequest);

        assertNotNull(response);

        assertEquals(
                "test-jwt-token",
                response.token()
        );

        assertEquals(
                "Bearer",
                response.type()
        );

        assertEquals(
                3600,
                response.expiresIn()
        );

        verify(authenticationManager, times(1))
                .authenticate(
                        any(UsernamePasswordAuthenticationToken.class)
                );

        verify(jwtService, times(1))
                .generateToken(authentication);
    }

    // --------------------------------------------------
    // LOGIN FAILURE
    // --------------------------------------------------

    @Test
    void shouldThrowExceptionWhenCredentialsAreInvalid() {

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenThrow(
                new BadCredentialsException(
                        "Invalid username or password"
                )
        );

        assertThrows(
                BadCredentialsException.class,
                () -> authenticationService.login(loginRequest)
        );

        verify(jwtService, never())
                .generateToken(any());
    }
}
