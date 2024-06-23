package com.homecooked.common.client.dto;

import com.homecooked.common.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientResponseDto extends UserResponseDto {

    @Builder.Default
    private String roleType = "CLIENT";
}
