package com.notes.controller;

import com.notes.entity.User;
import com.notes.entity.UserProfile;
import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import com.notes.model.request.RequestUserProfile;
import com.notes.service.UserService;
import com.notes.util.Constant;
import com.notes.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        List<User> users = this.userService.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable UUID id) {
        User user = this.userService.findUserById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                user);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ResponseDTO> getUserByUsername(@PathVariable String username) {
        User user = this.userService.findUserByUsername(username);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully retrieved data!"),
                user);
    }

    @PostMapping("/profile")
    public ResponseEntity<ResponseDTO> createUserProfile(@RequestBody RequestUserProfile requestUserProfile) {
        UserProfile userProfile = this.userService.createUserProfile(requestUserProfile);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully created profile!"),
                userProfile);
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseDTO> updateUserProfile(@RequestBody RequestUserProfile requestUserProfile) {
        UserProfile userProfile = this.userService.updateUserProfile(requestUserProfile);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully created profile!"),
                userProfile);
    }
}
