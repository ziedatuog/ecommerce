package com.northEthio.model;

import com.northEthio.domain.HomeCatagorySection;
import com.northEthio.domain.PaymentOrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HomeCatagory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String image;
    private PaymentOrderStatus catagoryId;
    private HomeCatagorySection section;
}
