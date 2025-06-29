package com.northEthio.service;

import com.northEthio.domain.AccountStatus;
import com.northEthio.model.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id, Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifiyEmail(String email, String otp) throws Exception;
    Seller updatSellerAccountStatus(Long id, AccountStatus status) throws Exception;
}
