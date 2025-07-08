package com.northEthio.service.impl;

import com.northEthio.config.JwtProvider;
import com.northEthio.domain.AccountStatus;
import com.northEthio.domain.USER_ROLE;
import com.northEthio.exception.SellerException;
import com.northEthio.model.Address;
import com.northEthio.model.Seller;
import com.northEthio.repository.AddressRepository;
import com.northEthio.repository.SellerRepository;
import com.northEthio.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {

        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist != null) {
            throw new Exception("seller already exist, used different email");
        }

        Address saveAddress = addressRepository.save(seller.getPickUpAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickUpAddress(saveAddress);
        newSeller.setGSTING(seller.getGSTING());
        newSeller.setRole(USER_ROLE.ROLE_CUSTOMER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBussinesDetails(seller.getBussinesDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {

        return sellerRepository.findById(id)
                .orElseThrow(()->new SellerException("seller not found with id "+id)
                );
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {

        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new Exception("seller not found ...");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {

        Seller existingSeller = this.getSellerById(id);
        if (seller.getSellerName()!=null) {
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getMobile()!=null) {
            existingSeller.setMobile(seller.getMobile());
        }
        if (seller.getEmail()!=null) {
            existingSeller.setEmail(seller.getEmail());
        }
        if (seller.getBussinesDetails()!=null
                && seller.getBussinesDetails().getBussinessName()!=null
        ) {
            existingSeller.getBussinesDetails().setBussinessName (
                    seller.getBussinesDetails().getBussinessName()
            );
        }

        if (seller.getBankDetails()!=null
                && seller.getBankDetails().getAccountHolderName()!=null
                && seller.getBankDetails().getIfscCode()!=null
                && seller.getBankDetails().getAccountNumber()!=null
        ) {
            existingSeller.getBankDetails().setAccountHolderName (
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setIfscCode (
                    seller.getBankDetails().getIfscCode()
            );
            existingSeller.getBankDetails().setAccountNumber (
                    seller.getBankDetails().getAccountNumber()
            );


        }
        if (seller.getPickUpAddress()!=null
                && seller.getPickUpAddress().getAddress()!=null
                && seller.getPickUpAddress().getMobile()!=null
                && seller.getPickUpAddress().getCity()!=null
                && seller.getPickUpAddress().getState()!=null
        ) {
            existingSeller.getPickUpAddress().setAddress (
                    seller.getPickUpAddress().getAddress()
            );
            existingSeller.getPickUpAddress().setMobile (
                    seller.getPickUpAddress().getMobile()
            );
            existingSeller.getPickUpAddress().setCity (
                    seller.getPickUpAddress().getCity()
            );
            existingSeller.getPickUpAddress().setState (
                    seller.getPickUpAddress().getState()
            );
            existingSeller.getPickUpAddress().setPinCode (
                    seller.getPickUpAddress().getPinCode()
            );


        }

        if (seller.getGSTING()!=null){
            existingSeller.setGSTING(seller.getGSTING());
        }
        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws SellerException {
        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);

    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setIsEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updatSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller=getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
