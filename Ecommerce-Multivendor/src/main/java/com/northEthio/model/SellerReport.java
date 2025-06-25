package com.northEthio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode


public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Seller seller;

    private Long totalEarnings=0L;
    private Long totalSells=0L;

    private Long Refunds= 0L;

    private Long totalTax = 0L;

    private Long netEarnings = 0L;
}
