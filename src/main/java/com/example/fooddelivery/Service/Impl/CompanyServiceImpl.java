package com.example.fooddelivery.Service.Impl;

import com.example.fooddelivery.Entity.Company;
import com.example.fooddelivery.Pojo.CompanyPojo;
import com.example.fooddelivery.Repository.CompanyRepo;
import com.example.fooddelivery.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service

public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    @Override
    public void addCompany(CompanyPojo companyPojo) {
        Company company = new Company();
        company.setId(companyPojo.getId());
        company.setName(companyPojo.getName());
        company.setLocation(companyPojo.getLocation());
        company.setEmail(companyPojo.getEmail());
        companyRepo.save(company);

    }

    @Override
    public void updateData(Integer id, CompanyPojo companyPojo) {
        Optional<Company> companyOptional = companyRepo.findById(id);
        if (companyOptional.isPresent()) {
            Company existingCompany = companyOptional.get();
            updateBuildingProperties(existingCompany, companyPojo);
            companyRepo.save(existingCompany); // Save the updated student
        } else {
            throw new IllegalArgumentException("Company with ID " + id + " not found");
        }

    }
    private void updateBuildingProperties(Company company, CompanyPojo companyPojo) {
        company.setId(companyPojo.getId());
        company.setName(companyPojo.getName());
        company.setLocation(companyPojo.getLocation());
//        company.setTelNo(companyPojo.getTelNo());
        company.setEmail(companyPojo.getEmail());
        this.companyRepo.save(company);

    }

    @Override
    public void deleteById(Integer id) {
        this.companyRepo.deleteById(id);
    }

    @Override
    public List<Company> getAll() {
        return this.companyRepo.findAll();
    }

    @Override
    public Optional<Company> findById(Integer id) {
        return this.companyRepo.findById(id);
    }


    @Override
    public boolean existsById(Integer id) {
        return this.companyRepo.existsById(id);
    }
}
