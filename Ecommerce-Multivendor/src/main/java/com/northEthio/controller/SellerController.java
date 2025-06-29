package com.northEthio.controller;

import com.northEthio.model.VerificationCode;
import com.northEthio.repository.SellerRepository;
import com.northEthio.request.LoginRequest;
import com.northEthio.response.AuthResponse;
import com.northEthio.response.VerificationCodeRepository;
import com.northEthio.service.AuthService;
import com.northEthio.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController  {
    private final SellerRepository sellerRepository;
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> LoginSeller(
            @RequestBody LoginRequest req) throws Exception {
        String otp=req.getOtp();
        String email=req.getEmail();
//        VerificationCode verificationCode=verificationCodeRepository.findByEmail(email);
//        if (verificationCode==null || verificationCode.getOtp().equals(req.getOtp())) {
//            throw new Exception("wrong otp");
//        }
        req.setEmail("seller_"+email);
        AuthResponse authResponse= authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }
}
