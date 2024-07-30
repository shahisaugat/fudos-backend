package com.example.fooddelivery.Service.Impl;

import com.example.fooddelivery.Entity.Categories;
import com.example.fooddelivery.Pojo.CategoryPojo;
import com.example.fooddelivery.Repository.CategoryRepo;
import com.example.fooddelivery.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class  CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    @Override
    public void saveData(CategoryPojo categoryPojo) {
        Categories categories=new Categories();
        categories.setCategoryName(categoryPojo.getCategoryName());
        categories.setDescription(categoryPojo.getDescription());
        categoryRepo.save(categories);

    }

    @Override
    public List<Categories> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepo.deleteById(id.intValue());

    }

    @Override
    public Optional<Categories> findById(Long id) {
        return categoryRepo.findById(id.intValue());
    }



    public void updateData(Integer id, CategoryPojo categoryPojo) {
        Optional<Categories> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            Categories existingCategory = categoryOptional.get();

            updateCategory(existingCategory, categoryPojo);
            categoryRepo.save(existingCategory);
        } else {

            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
    }
    private void updateCategory(Categories existingCategory, CategoryPojo categoryPojo) {
        if (categoryPojo.getCategoryName() != null) {
            existingCategory.setCategoryName(categoryPojo.getCategoryName());
        }
        if (categoryPojo.getDescription() != null) {
            existingCategory.setDescription(categoryPojo.getDescription());
        }
        // Add more fields as needed
    }


    @Override
    public boolean existsById(Integer id) {
        return categoryRepo.existsById(id.intValue());
    }
}
