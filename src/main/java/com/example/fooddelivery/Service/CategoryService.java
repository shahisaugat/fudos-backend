package com.example.fooddelivery.Service;
import com.example.fooddelivery.Entity.Categories;
import com.example.fooddelivery.Entity.Items;
import com.example.fooddelivery.Pojo.CategoryPojo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface CategoryService {
    void saveData(CategoryPojo categoryPojo);
    List<Categories> getAll();
    void deleteById(Long id);
    Optional<Categories> findById(Long id);
    void updateData(Integer id, CategoryPojo categoryPojo);
    boolean existsById(Integer id);
}
