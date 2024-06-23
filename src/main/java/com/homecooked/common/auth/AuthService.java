package com.homecooked.common.auth;


import com.homecooked.common.chef.Chef;
import com.homecooked.common.chef.ChefRepository;
import com.homecooked.common.chef.dto.ChefCreateUpdateDto;
import com.homecooked.common.chef.mapper.ChefMapper;
import com.homecooked.common.client.Client;
import com.homecooked.common.client.ClientRepository;
import com.homecooked.common.client.dto.ClientCreateUpdateDto;
import com.homecooked.common.client.mapper.ClientMapper;
import com.homecooked.common.constant.Role;
import com.homecooked.common.exception.VerificationException;
import com.homecooked.common.security.JwtResponse;
import com.homecooked.common.security.JwtService;
import com.homecooked.common.security.RoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ChefRepository chefRepository;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ChefMapper chefMapper;
    private final ClientMapper clientMapper;
    private static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password.";

    public JwtResponse signIn(AuthDto request, Role role) {
        UserDetails user = switch (role) {
            case CHEF -> chefRepository
                    .findByEmailIgnoreCase(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException(INVALID_EMAIL_OR_PASSWORD));
            case CLIENT -> clientRepository
                    .findByEmailIgnoreCase(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException(INVALID_EMAIL_OR_PASSWORD));
        };
        RoleContext.setRoleHolder(role);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new VerificationException("Invalid credentials provided.");
        }

        return jwtService.generateToken(user, role);
    }

    public JwtResponse registerChef(ChefCreateUpdateDto request) {
        if (chefRepository.findByEmailIgnoreCase(request.getEmail()).isPresent() ||
                chefRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Email or phone number is already in use.");
        }

        Chef newChef = chefMapper.createUpdateDtoToEntity(request);
        newChef.setPassword(passwordEncoder.encode(request.getPassword()));
        chefRepository.save(newChef);

        UserDetails user = chefRepository.findByEmailIgnoreCase(request.getEmail()).get();
        RoleContext.setRoleHolder(Role.CHEF);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return jwtService.generateToken(user, Role.CHEF);
    }

    public JwtResponse registerClient(ClientCreateUpdateDto request) {
        if (clientRepository.findByEmailIgnoreCase(request.getEmail()).isPresent() ||
                clientRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Email or phone number is already in use.");
        }

        Client newClient = clientMapper.createUpdateDtoToEntity(request);
        newClient.setPassword(passwordEncoder.encode(request.getPassword()));
        clientRepository.save(newClient);

        UserDetails user = clientRepository.findByEmailIgnoreCase(request.getEmail()).get();
        RoleContext.setRoleHolder(Role.CLIENT);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return jwtService.generateToken(user, Role.CLIENT);
    }

    public Chef getCurrentChef() {
        String email = getCurrentUserEmail();
        return chefRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new VerificationException("Current user is not a Chef or not found."));
    }

    public Client getCurrentClient() {
        String email = getCurrentUserEmail();
        return clientRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new VerificationException("Current user is not a Client or not found."));
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new VerificationException("No authenticated user found.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername(); // Username in Spring Security typically corresponds to email for many systems.
    }

}
