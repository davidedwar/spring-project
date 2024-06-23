package com.homecooked.common.security;

import com.homecooked.common.chef.ChefRepository;
import com.homecooked.common.chef.mapper.ChefMapper;
import com.homecooked.common.client.ClientRepository;
import com.homecooked.common.client.mapper.ClientMapper;
import com.homecooked.common.constant.Role;
import com.homecooked.common.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND = "User not found";
    private final ClientRepository clientRepository;
    private final ChefRepository chefRepository;
    private final ClientMapper clientMapper;
    private final ChefMapper chefMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> {
            Role role = RoleContext.getRole();
            return switch (role) {
                case CLIENT -> clientRepository
                        .findByEmailIgnoreCase(email)
                        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
                case CHEF -> chefRepository
                        .findByEmailIgnoreCase(email)
                        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
            };
        };
    }

    @Override
    public UserResponseDto findByRoleAndEmail(Role role, String email) {
        return switch (role) {
            case CHEF -> chefMapper.toUserResponseDto(chefRepository.findByEmailIgnoreCase(email).orElseThrow());
            case CLIENT -> clientMapper.toUserResponseDto(clientRepository.findByEmailIgnoreCase(email).orElseThrow());
        };
    }

}
