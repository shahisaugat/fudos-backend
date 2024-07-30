package com.example.fooddelivery.Service;

import com.example.fooddelivery.Entity.Product;
import com.example.fooddelivery.Pojo.ProductPojo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(ProductPojo productPojo, MultipartFile image) throws IOException;
    void deleteById(Long id);
    void updateData(Long id, ProductPojo productPojo, MultipartFile image) throws IOException;
    byte [] getProductImage(Integer productId) throws IOException;
    boolean existsById(Long id);
    Optional<Product> findById(Long id);
    List<Product> getAll();
    Long productCount();


}
