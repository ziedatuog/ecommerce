package com.northEthio.controller;

import com.northEthio.domain.USER_ROLE;
import com.northEthio.model.User;
import com.northEthio.model.VerificationCode;
import com.northEthio.repository.ApiResponse;
import com.northEthio.repository.UserRepository;
import com.northEthio.request.LoginRequest;
import com.northEthio.response.AuthResponse;
import com.northEthio.response.SignupRequest;
import com.northEthio.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {



    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
       String jwt = authService.createUser(req);

       AuthResponse res = new AuthResponse();
       res.setJwt(jwt);
       res.setMessage("register success");
       res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody VerificationCode req) throws Exception {
         authService.sentLoginOtp(req.getEmail());

        ApiResponse res = new ApiResponse();

        res.setMessage("otp sent success");


        return ResponseEntity.ok(res);
    }


    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> LoginHandler(
            @RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse =  authService.signing(req);

        ApiResponse res = new ApiResponse();

        res.setMessage("otp sent success");


        return ResponseEntity.ok(authResponse);
    }

}
