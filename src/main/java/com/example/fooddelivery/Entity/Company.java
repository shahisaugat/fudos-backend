package com.example.fooddelivery.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table

@Setter
@Getter
public class Company {
    @Id
    private Integer id;


    @Column(
            name = "Name"
    )
    private String name;
    @Column(
            name = "Location"
    )
    private String location;
    @Column(
            name = "Email"
    )
    private String email;

}
