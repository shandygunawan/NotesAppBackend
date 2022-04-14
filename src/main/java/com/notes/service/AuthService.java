package com.notes.service;

import com.notes.entity.User;
import com.notes.model.request.RequestLogin;
import com.notes.repository.UserRepository;
import com.notes.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        this.userRepository.save(newUser);
        return jwtUtil.generateToken(newUser.getUsername());
    }

    public String login(RequestLogin requestLogin) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(requestLogin.getUsername(), requestLogin.getPassword());

        this.authenticationManager.authenticate(authToken);

        return jwtUtil.generateToken(requestLogin.getUsername());
    }

}
