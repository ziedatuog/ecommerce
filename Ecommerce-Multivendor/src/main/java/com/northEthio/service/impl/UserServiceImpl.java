package com.northEthio.service.impl;

import com.northEthio.config.JwtProvider;
import com.northEthio.model.User;
import com.northEthio.repository.UserRepository;
import com.northEthio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);

        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found with email - " +email);
        }
        return user;
    }
}
