package com.notes.service;

import com.notes.entity.User;
import com.notes.entity.UserProfile;
import com.notes.model.request.RequestUserProfile;
import com.notes.repository.UserProfileRepository;
import com.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository profileRepository;

    /*
        GET
    */
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    /*
        CREATE
    */
    public UserProfile createUserProfile(RequestUserProfile requestUserProfile) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(this.userRepository.findByUsername(this.getCurrentLoggedInUsername()));
        userProfile.setEmail(requestUserProfile.getEmail());
        userProfile.setPhoneNumber(requestUserProfile.getPhoneNumber());
        return this.profileRepository.save(userProfile);
    }

    /*
        UPDATE
    */
    public UserProfile updateUserProfile(RequestUserProfile requestUserProfile) {
        User user = this.userRepository.findByUsername(this.getCurrentLoggedInUsername());
        UserProfile userProfile = user.getUserProfile();
        userProfile.setEmail(requestUserProfile.getEmail());
        userProfile.setPhoneNumber(requestUserProfile.getPhoneNumber());
        return this.profileRepository.save(userProfile);
    }

    /*
        UTILS
    */
    private String getCurrentLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
