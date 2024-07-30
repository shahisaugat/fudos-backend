package com.example.fooddelivery.Controller;

import com.example.fooddelivery.Entity.Items;
import com.example.fooddelivery.Pojo.ItemsPojo;
import com.example.fooddelivery.Service.ItemService;
import com.example.fooddelivery.Shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")

public class ItemsController {
    private final ItemService itemService;
    @GetMapping(value = "/get", produces = "application/json")
    public GlobalApiResponse<List<Items>> getData() {
        List<Items> items = itemService.getAll();
        return GlobalApiResponse
                .<List<Items>>builder()
                .data(items)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody ItemsPojo itemsPojo) {
        if (!itemService.existsById(id)) {
            return new ResponseEntity<>("Customer id" + id + " not found", HttpStatus.NOT_FOUND);
        } else {
            itemService.updateData(id, itemsPojo);

        }
        return ResponseEntity.ok("Items with ID " + id + " updated successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ItemsPojo itemsPojo) {

        itemService.saveData(itemsPojo);
        return ResponseEntity.ok("items saved successfully");
    }
    @GetMapping("/get/{id}")
    public Optional<Items> getData(@PathVariable Integer id) {

        return itemService.findById(id.longValue());
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        this.itemService.deleteById(id.longValue());
    }
}
