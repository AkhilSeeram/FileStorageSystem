package com.vcriate.demo.controllers;

import com.vcriate.demo.dtos.LoginRequestDto;
import com.vcriate.demo.dtos.SignupRequestDto;
import com.vcriate.demo.exceptions.EntityNotFoundException;
import com.vcriate.demo.models.File;
import com.vcriate.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    public UserController(UserService userService, AuthenticationManager authenticationManager){
        this.userService=userService;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto){
        userService.signupUser(signupRequestDto.getEmail(),signupRequestDto.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), loginRequestDto.getPassword()
        ));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userService.loginUser(loginRequestDto.getEmail(),loginRequestDto.getPassword());
            String token=userService.loginUser(loginRequestDto.getEmail(),loginRequestDto.getPassword());
            return ResponseEntity.ok(token);
        }
        else {
            throw  new EntityNotFoundException("Enter Correct Username or Password");
        }
    }

    @GetMapping("/files")
    public ResponseEntity<Set<File>> getFilesOfUser() {
//      Authentication object is updated in JwtAuthentication filter.
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Set<File> files = userService.getFilesOfUser(email);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/shared-files")
    public ResponseEntity<Set<File>> getSharedFilesSharedToUser(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Set<File> filesSharedToUser = userService.getFilesSharedToUser(email);
        return ResponseEntity.ok(filesSharedToUser);
    }

}
