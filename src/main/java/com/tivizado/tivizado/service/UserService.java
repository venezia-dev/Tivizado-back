package com.tivizado.tivizado.service;

import com.tivizado.tivizado.exception.UserNotFoundException;
import com.tivizado.tivizado.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void saveUser(User user);
    public User getUserByNameAndPassword(String name, String password) throws UserNotFoundException;
}