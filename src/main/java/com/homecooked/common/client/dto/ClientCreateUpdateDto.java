package com.homecooked.common.client.dto;

import com.homecooked.common.user.dto.UserCreateUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientCreateUpdateDto extends UserCreateUpdateDto {

}

