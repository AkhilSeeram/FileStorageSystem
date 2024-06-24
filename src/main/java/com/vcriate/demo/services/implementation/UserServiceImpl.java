package com.vcriate.demo.services.implementation;

import com.vcriate.demo.exceptions.EntityNotFoundException;
import com.vcriate.demo.exceptions.UserAlreadyExistsException;
import com.vcriate.demo.models.File;
import com.vcriate.demo.models.User;
import com.vcriate.demo.repositories.UserRepository;
import com.vcriate.demo.security.JwtService;
import com.vcriate.demo.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    @Override
    public User signupUser(String email, String password) {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("email already registered");
        }
        User user=new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public String loginUser(String email, String password) {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        String token= jwtService.generateToken(optionalUser.get());
        return token;
    }

    @Override
    public Set<File> getFilesOfUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getFiles();
    }

    @Override
    public Set<File> getFilesSharedToUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getSharedFiles();
    }
}
