package com.example.fooddelivery.Service.Impl;

import com.example.fooddelivery.Entity.Items;
import com.example.fooddelivery.Pojo.ItemsPojo;
import com.example.fooddelivery.Repository.ItemsRepo;
import com.example.fooddelivery.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service

public class ItemServiceImpl implements ItemService {
    private final ItemsRepo itemsRepo;
    @Override
    public void saveData(ItemsPojo itemsPojo) {
        Items items=new Items();
        items.setId(itemsPojo.getId());
        items.setItemName(itemsPojo.getItemName());
        items.setPrice(itemsPojo.getPrice());
        items.setItemDetails(itemsPojo.getItemDetails());
        itemsRepo.save(items);

    }

    @Override
    public List<Items> getAll() {
        return itemsRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        itemsRepo.deleteById(id.intValue());

    }

    @Override
    public Optional<Items> findById(Long id) {
        return itemsRepo.findById(id.intValue());
    }

    @Override
    public void updateData(Integer id, ItemsPojo itemsPojo) {
        Optional<Items> itemsOptional = itemsRepo.findById(id);

            Items existingItems = itemsOptional.get();
            // Update the existing student with the data from studentPojo
            updateItemsProperties(existingItems, itemsPojo);
            itemsRepo.save(existingItems); // Save the updated student

    }

    // Helper method to update properties of Student based on StudentPojo
    private void updateItemsProperties(Items items, ItemsPojo itemsPojo) {
        items.setItemName(itemsPojo.getItemName());
        items.setItemDetails(itemsPojo.getItemDetails());
        items.setId(itemsPojo.getId());
        itemsRepo.save(items);
    }



    @Override
    public boolean existsById(Integer id) {
        return itemsRepo.existsById(id.intValue());
    }
}
