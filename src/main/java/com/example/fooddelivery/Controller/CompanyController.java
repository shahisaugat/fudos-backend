package com.example.fooddelivery.Controller;

import com.example.fooddelivery.Entity.Company;
import com.example.fooddelivery.Pojo.CompanyPojo;
import com.example.fooddelivery.Service.CompanyService;
import com.example.fooddelivery.Shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor


public class CompanyController {
    @Autowired

    private final CompanyService companyService;


    @GetMapping("/get")
    public GlobalApiResponse<List<Company>> getData() {
        List<Company> companies = companyService.getAll();
        return GlobalApiResponse.<List<Company>>builder()
                .data(companies)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }

    @PostMapping("/save")
    public GlobalApiResponse<Void> save(@RequestBody CompanyPojo companyPojo) {
        companyService.addCompany(companyPojo);
        return GlobalApiResponse.<Void>builder()
                .statusCode(201)
                .message("Company saved successfully!")
                .build();
    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Company> getData(@PathVariable Integer id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            return GlobalApiResponse.<Company>builder()
                    .data(company.get())
                    .statusCode(200)
                    .message("Building retrieved successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Company>builder()
                    .statusCode(404)
                    .message("company not found!")
                    .build();
        }
    }
    @PutMapping("/update/{id}")
    public GlobalApiResponse<Void> update(@PathVariable Integer id, @RequestBody CompanyPojo companyPojo) {
        if (!companyService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        // Update the existing ground with the provided ID
        companyService.updateData(id, companyPojo);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("company with ID " + id + " updated successfully")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Integer id) {
        if (!companyService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("company with ID " + id + " not found")
                    .build();
        }

        companyService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Company with ID " + id + " deleted successfully")
                .build();
    }

}
