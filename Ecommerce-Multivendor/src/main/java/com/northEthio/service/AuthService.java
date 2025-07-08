package com.northEthio.service;

import com.northEthio.domain.USER_ROLE;
import com.northEthio.request.LoginRequest;
import com.northEthio.response.AuthResponse;
import com.northEthio.response.SignupRequest;


public interface AuthService {

    void sentLoginOtp (String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;

}
