package com.example.fooddelivery.Pojo;
import com.example.fooddelivery.Entity.Categories;
import com.example.fooddelivery.Entity.Items;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ItemsPojo {
    @Id
    private Integer id;

    private Integer price;
    private String itemName;
    private String itemDetails;
    private Categories category;

}
