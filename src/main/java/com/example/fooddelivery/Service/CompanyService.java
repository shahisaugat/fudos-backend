package com.example.fooddelivery.Service;


import com.example.fooddelivery.Entity.Company;
import com.example.fooddelivery.Pojo.CompanyPojo;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    void addCompany(CompanyPojo companyPojo);

    void deleteById(Integer id);

    List<Company> getAll();

    Optional<Company> findById(Integer id);
    void updateData(Integer id, CompanyPojo companyPojo);
    boolean existsById(Integer id);
}
