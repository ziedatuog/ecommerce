package com.northEthio.service;

import com.northEthio.response.SignupRequest;

public interface AuthService {
    String createUser(SignupRequest req);

}
