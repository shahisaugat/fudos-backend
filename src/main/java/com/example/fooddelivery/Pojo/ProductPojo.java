package com.example.fooddelivery.Pojo;

import com.example.fooddelivery.Entity.FileData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPojo {
    private Long productId;
    private String productName;
    private String detail;
    private Long price;
    private FileData imageData;
}
