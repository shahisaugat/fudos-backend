package com.example.fooddelivery.Service.Impl;

import com.example.fooddelivery.Entity.FileData;
import com.example.fooddelivery.Entity.Product;
import com.example.fooddelivery.Pojo.ProductPojo;
import com.example.fooddelivery.Repository.ProductRepo;
import com.example.fooddelivery.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final StorageService storageService;

    @Override
    public void addProduct(ProductPojo productPojo, MultipartFile image) throws IOException {
        String fileName = storageService.uploadImageToFileSystem(image);
        FileData imageData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();

        Product product = new Product();
        product.setProductName(productPojo.getProductName());
        product.setPrice(productPojo.getPrice());
        product.setDetail(productPojo.getDetail());
        product.setImageData(imageData);

        productRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public void updateData(Long id, ProductPojo productPojo, MultipartFile image) throws IOException {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductName(productPojo.getProductName());
            product.setPrice(productPojo.getPrice());
            product.setDetail(productPojo.getDetail());

            if (image != null && !image.isEmpty()) {
                String fileName = storageService.uploadImageToFileSystem(image);
                FileData imageData = FileData.builder()
                        .name(fileName)
                        .type(image.getContentType())
                        .filePath(storageService.FOLDER_PATH + fileName)
                        .build();
                product.setImageData(imageData);
            }
            productRepo.save(product);
        }
    }

    @Override
    public byte[] getProductImage(Integer productId) throws IOException {
        System.out.println("Fetching image for product ID: " + productId);
        Optional<Product> optionalProduct = productRepo.findById(productId.longValue());
        if (optionalProduct.isPresent() && optionalProduct.get().getImageData() != null) {
            String fileName = optionalProduct.get().getImageData().getName();
            if (fileName != null) {
                System.out.println("Found image file name: " + fileName);
                byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
                if (imageData == null) {
                    System.out.println("Failed to retrieve image data for file: " + fileName);
                }
                return imageData;
            } else {
                System.out.println("Image file name is null for product ID: " + productId);
            }
        } else {
            System.out.println("No image data found for product ID: " + productId);
        }
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return productRepo.existsById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public Long productCount() {
        return productRepo.count();
    }
}
