package com.example.fooddelivery.Controller;

import com.example.fooddelivery.Entity.Product;
import com.example.fooddelivery.Pojo.ProductPojo;
import com.example.fooddelivery.Service.ProductService;
import com.example.fooddelivery.Shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Product> addProduct(@RequestPart("product") ProductPojo productPojo,
                                                 @RequestPart("image") MultipartFile image) {
        try {
            productService.addProduct(productPojo, image);
            return GlobalApiResponse.<Product>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Product added successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Product>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to process image!")
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Product with ID " + id + " not found")
                    .build();
        }

        productService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product with ID " + id + " deleted successfully")
                .build();
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Product> updateProduct(@PathVariable Long id,
                                                    @RequestPart("product") ProductPojo productPojo,
                                                    @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            productService.updateData(id, productPojo, image);
            Product updatedProduct = productService.findById(id).orElse(null);
            return GlobalApiResponse.<Product>builder()
                    .data(updatedProduct)
                    .statusCode(HttpStatus.OK.value())
                    .message("Product updated successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Product>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to process image!")
                    .build();
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getHomeImage(@PathVariable Integer id) {
        try {
            byte[] imageData = productService.getProductImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/exists")
    public GlobalApiResponse<Boolean> existsById(@PathVariable Long id) {
        boolean exists = productService.existsById(id);
        return GlobalApiResponse.<Boolean>builder()
                .message("Check product existence.")
                .data(exists)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/{id}")
    public GlobalApiResponse<Product> findById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return GlobalApiResponse.<Product>builder()
                    .message("Product found.")
                    .data(product.get())
                    .statusCode(HttpStatus.OK.value())
                    .build();
        } else {
            return GlobalApiResponse.<Product>builder()
                    .message("Product not found.")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }
    }

    @GetMapping("/get")
    public GlobalApiResponse<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();
        return GlobalApiResponse.<List<Product>>builder()
                .data(products)
                .statusCode(HttpStatus.OK.value())
                .message("Data retrieved successfully!")
                .build();
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> productCount() {
        Long count = productService.productCount();
        return GlobalApiResponse.<Long>builder()
                .data(count)
                .statusCode(HttpStatus.OK.value())
                .message("Product count retrieved successfully!")
                .build();
    }
}
