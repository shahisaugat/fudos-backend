package com.example.fooddelivery.Pojo;


import com.example.fooddelivery.Entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor



public class PaymentPojo {
    private Integer id;

    private String type;

    private String amount;
    private LocalDate date;
    private Orders order;
}

