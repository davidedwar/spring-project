package com.homecooked.common.security;

import com.homecooked.common.constant.Role;
import com.homecooked.common.user.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    UserResponseDto findByRoleAndEmail(Role role, String email);
}
