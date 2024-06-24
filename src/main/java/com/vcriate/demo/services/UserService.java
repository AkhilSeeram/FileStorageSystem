package com.vcriate.demo.services;

import com.vcriate.demo.models.File;
import com.vcriate.demo.models.User;

import java.util.Set;

public interface UserService {
    public User signupUser(String email, String password);
    public User loginUser(String email, String password);
    public Set<File> getFilesOfUser(Long userId);
    public Set<File> getFilesSharedToUser(Long userId);
}
