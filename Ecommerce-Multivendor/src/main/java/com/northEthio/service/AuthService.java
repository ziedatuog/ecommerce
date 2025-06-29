package com.northEthio.service;

import com.northEthio.request.LoginRequest;
import com.northEthio.response.AuthResponse;
import com.northEthio.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp (String email) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) ;

}
