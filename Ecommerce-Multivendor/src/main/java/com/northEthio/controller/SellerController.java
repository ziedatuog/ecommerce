package com.northEthio.controller;

import com.northEthio.config.JwtProvider;
import com.northEthio.domain.AccountStatus;
import com.northEthio.exception.SellerException;
import com.northEthio.model.Seller;
import com.northEthio.model.SellerReport;
import com.northEthio.model.VerificationCode;
import com.northEthio.repository.SellerRepository;
import com.northEthio.request.LoginRequest;
import com.northEthio.response.AuthResponse;
import com.northEthio.response.VerificationCodeRepository;
import com.northEthio.service.AuthService;
import com.northEthio.service.EmailService;
import com.northEthio.service.SellerService;
import com.northEthio.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController  {
    private final SellerRepository sellerRepository;
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> LoginSeller(
            @RequestBody LoginRequest req) throws Exception {
        String otp=req.getOtp();
        String email=req.getEmail();
        req.setEmail("seller_"+email);
        AuthResponse authResponse= authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(
            @PathVariable String otp) throws Exception {
        VerificationCode verificationCode=verificationCodeRepository.findByOtp(otp);

        if(verificationCode==null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("wrong otp");
        }

        Seller seller=sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(
            @RequestBody Seller seller) throws Exception {
        Seller savedSeller=sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "North Ethio verification code";
        String text = "Welcome to North Ethio, verify account using this link";
        String frontend_url = "https://localhost:3000/verify-seller/";
        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);

        return new ResponseEntity<>(seller, HttpStatus.OK);
      }
      @GetMapping("/{id}")
      public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller=sellerService.getSellerById(id) ;
        return new ResponseEntity<>(seller, HttpStatus.OK);
      }

      @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt ) throws Exception
      {
//          String email = jwtProvider.getEmailFromJwtToken(jwt);
//          Seller seller=sellerService.getSellerByEmail(email);
          Seller seller = sellerService.getSellerProfile(jwt);
          return new ResponseEntity<>(seller, HttpStatus.OK);
      }

//      public ResponseEntity<SellerReport> getSellerReport(
//        @RequestHeader("Authorizaation") String jwt) throws Exception{
//        String email = jwtProvider.getEmailFromJwtToken(jwt);
//          Seller seller = sellerService.getSellerProfile(jwt);
//          SellerReport report = sellerReportService.getSellerReport(seller);
//          return new ResponseEntity<>(report, HttpStatus.OK);
//
//
//      }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSeller(
            @RequestParam(required = false)AccountStatus status){
        List<Seller> sellers = sellerService .getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @PatchMapping
    public ResponseEntity<Seller> updateSeller(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Seller seller) throws Exception {
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {
     sellerService.deleteSeller(id);
     return ResponseEntity.noContent().build();
    }



}
