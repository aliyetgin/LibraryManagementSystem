package com.library.services;

import com.library.models.User;
import com.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public boolean addUser(User user) {
        // Validate user details
        if (user.getName() == null || user.getEmail() == null || user.getRole() == null) {
            return false;
        }
        userRepository.save(user);  // Save user to the database
        return true;
    }
}
