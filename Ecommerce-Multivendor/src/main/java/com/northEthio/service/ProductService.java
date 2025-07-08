package com.northEthio.service;

import com.northEthio.exception.ProductException;
import com.northEthio.model.Product;
import com.northEthio.model.Seller;
import com.northEthio.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    Product findProductById(Long productId) throws ProductException;
    List<Product>searchProducts();
    public Page<Product> getAllProducts(
            String catagory,
            String Brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    List<Product> findProductBySellerId(Long sellerId);
}
