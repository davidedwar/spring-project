package com.homecooked.common.chef.dto;

import com.homecooked.common.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChefResponseDto extends UserResponseDto {

}
