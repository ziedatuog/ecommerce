package com.northEthio.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
public class Home {
    private List<HomeCatagory> grid;
    private List<HomeCatagory> shopByCatagories;
    private List<HomeCatagory> electricCatagories;
    private List<HomeCatagory> dealCatagories;
    private List<Deal> deals;
}
