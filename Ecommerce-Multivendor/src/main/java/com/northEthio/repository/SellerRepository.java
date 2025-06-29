package com.northEthio.repository;

import com.northEthio.domain.AccountStatus;
import com.northEthio.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findByEmail(String email);
    List<Seller> findAccountStatus(AccountStatus status);
}
