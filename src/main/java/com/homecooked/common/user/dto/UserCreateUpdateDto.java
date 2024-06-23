package com.homecooked.common.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateUpdateDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "password is required")
    private String password;
    private String firstName;
    private String lastName;
    private String status;
    private LocalDateTime birthDate;
    private String language;
    private String nationality;
    private boolean sex;

}
