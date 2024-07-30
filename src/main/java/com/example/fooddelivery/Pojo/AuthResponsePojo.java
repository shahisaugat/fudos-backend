package com.example.fooddelivery.Pojo;

import com.example.fooddelivery.Entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponsePojo {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private Role role;
}
