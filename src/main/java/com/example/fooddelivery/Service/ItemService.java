package com.example.fooddelivery.Service;

import com.example.fooddelivery.Entity.Items;
import com.example.fooddelivery.Pojo.ItemsPojo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface ItemService {
    void saveData(ItemsPojo itemsPojo);
    List<Items> getAll();
    void deleteById(Long id);
    Optional<Items> findById(Long id);
    void updateData(Integer id, ItemsPojo itemsPojo);
    boolean existsById(Integer id);
}
