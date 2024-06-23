package com.homecooked.common.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String sex;
    private String city;
    private String firstName;
    private String lastName;
    private String weight;
}