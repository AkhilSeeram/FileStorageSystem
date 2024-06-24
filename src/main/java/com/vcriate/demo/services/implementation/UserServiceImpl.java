package com.vcriate.demo.services.implementation;

import com.vcriate.demo.exceptions.EntityNotFoundException;
import com.vcriate.demo.exceptions.UserAlreadyExistsException;
import com.vcriate.demo.models.File;
import com.vcriate.demo.models.User;
import com.vcriate.demo.repositories.UserRepository;
import com.vcriate.demo.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public User signupUser(String email, String password) {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("email already registered");
        }
        User user=new User();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("email is not registered");
        }
        return optionalUser.get();
    }

    @Override
    public Set<File> getFilesOfUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getFiles();
    }

    @Override
    public Set<File> getFilesSharedToUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getSharedFiles();
    }
}
