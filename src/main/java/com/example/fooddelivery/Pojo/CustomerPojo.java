package com.example.fooddelivery.Pojo;

import com.example.fooddelivery.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor



@Getter
@Setter
public class CustomerPojo {
    private Integer id;


    private String name;

    private String location;

    private String contactEmail;
    private String username;
    private String password;
    private List<Role> roles = new ArrayList<>();
}

