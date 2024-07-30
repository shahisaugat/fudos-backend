package com.example.fooddelivery.Pojo;

import jakarta.persistence.Column;
import lombok.Data;

@Data

public class CategoryPojo {

    private Integer id;

    private String categoryName;

    private  String description;

}
