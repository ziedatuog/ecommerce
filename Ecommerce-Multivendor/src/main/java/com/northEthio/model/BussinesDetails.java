package com.northEthio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BussinesDetails {

    private String bussinessName;
    private String bussinessEmail;
    private String bussinessMobile;
    private String bussinessAddress;
    private String logo;
    private String banner;
}
