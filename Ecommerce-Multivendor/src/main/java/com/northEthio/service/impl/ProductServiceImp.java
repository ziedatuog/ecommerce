package com.northEthio.service.impl;

import com.northEthio.exception.ProductException;
import com.northEthio.model.Category;
import com.northEthio.model.Product;
import com.northEthio.model.Seller;
import com.northEthio.repository.CategoryRepository;
import com.northEthio.repository.ProductRepository;
import com.northEthio.request.CreateProductRequest;
import com.northEthio.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.vocabulary.OWLTest.size;
import static org.apache.jena.vocabulary.SchemaDO.color;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Category category1=categoryRepository.findByCategoryId(req.getCategory());

        if(category1==null){
            Category category=new Category();
            category.setCatagoryId(req.getCategory());
            category.setLevel(1);
            category1=categoryRepository.save(category);
        }

        Category category2=categoryRepository.findByCategoryId(req.getCategory2());

        if(category2==null){
            Category category=new Category();
            category.setCatagoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2=categoryRepository.save(category);
        }

        Category category3=categoryRepository.findByCategoryId(req.getCategory3());

        if(category3==null){
            Category category=new Category();
            category.setCatagoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3=categoryRepository.save(category);
        }

        int discountPercentage=calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());

        Product product=new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSize());
        product.setDiscountPercent(discountPercentage);

        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice<=0){
            throw new IllegalArgumentException("mrpPrice must be greater than 0");
        }
        double discount = mrpPrice-sellingPrice;
        double discountPercentage=(discount/mrpPrice)*100;

        return (int)discountPercentage;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        productRepository.delete(product);

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        return productRepository.findById(productId).orElseThrow(()->
                new ProductException("product not found with id "+productId));
    }

    @Override
    public List<Product> searchProducts() {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(String catagory, String Brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        Specification<Product> spec = (root, query, criteriaBuilder)-> {
             List<Predicate> predicates = new ArrayList<>();

             if(catagory!=null){
                 Join<Product, Category> categoryJoin=root.join("category");
                 predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), catagory));
             }
             if(colors!=null &&!colors.isEmpty()){
                 System.out.println("color: "+colors);
                 predicates.add(criteriaBuilder.equal(root.get("color"), color));

             }
             if(sizes!=null &&!sizes.isEmpty()){
                 predicates.add(criteriaBuilder.equal(root.get("size"), sizes));
             }

             if(minPrice!=null){
                 predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
             }
             if(maxPrice!=null){
                 predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
             }
             if(minDiscount!=null){
                 predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"),
                         minDiscount));
             }
             if(stock!=null){
                 predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
             }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable;
        if(sort!=null &&!sort.isEmpty()){
            pageable = switch (sort){
                case "price_low"-> PageRequest.of(pageNumber!=null?pageNumber:0, 10,
                            Sort.by("sellingPrice").ascending());

                case "price_high"-> PageRequest.of(pageNumber!=null?pageNumber:0, 10,
                            Sort.by("sellingPrice").descending());

                default->  PageRequest.of(pageNumber!=null?pageNumber:0, 10,
                            Sort.unsorted());
            };
        }
        else{
            pageable=PageRequest.of(pageNumber!=null ? pageNumber:0,10, Sort.unsorted());
        }
        productRepository.findAll();
        return null;
    }

    @Override
    public List<Product> findProductBySellerId(Long sellerId) {
        return List.of();
    }
}
