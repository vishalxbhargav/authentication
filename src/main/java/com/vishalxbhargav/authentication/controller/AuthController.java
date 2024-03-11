package com.vishalxbhargav.authentication.controller;

import com.vishalxbhargav.authentication.config.JwtProvider;
import com.vishalxbhargav.authentication.request.LoginRequest;
import com.vishalxbhargav.authentication.response.AuthResponse;
import com.vishalxbhargav.authentication.service.CustomUserDetailsService;
import com.vishalxbhargav.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishalxbhargav.authentication.model.User;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customeUserDetailsService;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody User user) throws Exception {
        try {
            User isExist = userService.findbyEmail(user.getEmail());
            if (isExist != null) throw new Exception("Email alredy used with another account");

            User savedUser = userService.createUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            String token = JwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse(token, "Registerd Successfully");
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> singin(@RequestBody LoginRequest login) {
        try {
            Authentication authentication = authenticate(login);
            //generate token
            String token = JwtProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse(token, "Login Successfully");
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Authentication authenticate(LoginRequest login) {
        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(login.getEmail());
        if (userDetails == null) throw new BadCredentialsException("Invalid user");
        if (!passwordEncoder.matches(login.getPassword(), userDetails.getPassword()))
            throw new BadCredentialsException("Wrong Password");
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}