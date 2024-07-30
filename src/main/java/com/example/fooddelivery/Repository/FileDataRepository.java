package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Long> {
    List<FileData> findByName(String fileName);
}
