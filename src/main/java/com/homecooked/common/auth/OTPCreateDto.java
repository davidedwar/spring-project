package com.homecooked.common.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OTPCreateDto {

    private String phone;
    private String email;
    private String channel;
    private String code;

    public String getDependingOnChannel() {
        if (channel.equals("email")) {
            return email;
        } else {
            return phone;
        }
    }
}
