package com.notes.controller;

import com.notes.entity.User;
import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import com.notes.model.request.RequestLogin;
import com.notes.service.AuthService;
import com.notes.util.Constant;
import com.notes.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody User paramUser) {
        log.info(this.getClass().getName() + " - register: " + paramUser.toString());
        String authToken = this.authService.register(paramUser);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully register new user!"),
                authToken);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody RequestLogin requestLogin) {
        log.info(this.getClass().getName() + " - login: " + requestLogin.toString());
        String authToken = this.authService.login(requestLogin);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                new ErrorSchemaDTO(Constant.ErrorCode.SUCCESS, "Successfully login!"),
                authToken);
    }


}
