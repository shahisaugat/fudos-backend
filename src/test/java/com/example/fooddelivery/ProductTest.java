package com.example.fooddelivery;

import com.example.fooddelivery.Entity.FileData;
import com.example.fooddelivery.Entity.Product;
import com.example.fooddelivery.Pojo.ProductPojo;
import com.example.fooddelivery.Repository.ProductRepo;
import com.example.fooddelivery.Service.Impl.ProductServiceImpl;
import com.example.fooddelivery.Service.Impl.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() throws IOException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setProductName("Test Product");
        productPojo.setPrice((long) 100.0);
        productPojo.setDetail("Test Detail");

        MultipartFile image = mock(MultipartFile.class);
        String fileName = "test-image.png";

        when(storageService.uploadImageToFileSystem(image)).thenReturn(fileName);

        FileData fileData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();

        productService.addProduct(productPojo, image);

        verify(storageService, times(1)).uploadImageToFileSystem(image);
        verify(productRepo, times(1)).save(argThat(product ->
                product.getProductName().equals(productPojo.getProductName()) &&
                        product.getPrice().equals(productPojo.getPrice()) &&
                        product.getDetail().equals(productPojo.getDetail()) &&
                        product.getImageData().equals(fileData)
        ));
    }

    @Test
    public void testDeleteById() {
        Long productId = 1L;

        productService.deleteById(productId);

        verify(productRepo, times(1)).deleteById(productId);
    }

    @Test
    public void testUpdateData() throws IOException {
        Long productId = 1L;
        ProductPojo productPojo = new ProductPojo();
        productPojo.setProductName("Updated Product");
        productPojo.setPrice((long) 150.0);
        productPojo.setDetail("Updated Detail");

        MultipartFile image = mock(MultipartFile.class);
        String fileName = "updated-image.png";

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);

        when(productRepo.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(storageService.uploadImageToFileSystem(image)).thenReturn(fileName);

        FileData fileData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();

        productService.updateData(productId, productPojo, image);

        verify(productRepo, times(1)).save(argThat(product ->
                product.getProductName().equals(productPojo.getProductName()) &&
                        product.getPrice().equals(productPojo.getPrice()) &&
                        product.getDetail().equals(productPojo.getDetail()) &&
                        product.getImageData().equals(fileData)
        ));
    }



    @Test
    public void testFindById() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(productId);

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    public void testGetAll() {
        Product product = new Product();
        when(productRepo.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.getAll();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    public void testProductCount() {
        Long count = 10L;
        when(productRepo.count()).thenReturn(count);

        Long productCount = productService.productCount();

        assertEquals(count, productCount);
    }
}
