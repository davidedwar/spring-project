package com.homecooked.common.client;

import com.homecooked.common.client.dto.ClientCreateUpdateDto;
import com.homecooked.common.client.dto.ClientResponseDto;
import com.homecooked.common.client.mapper.ClientMapper;
import com.homecooked.common.constant.Role;
import com.homecooked.common.exception.EntityDeletedException;
import com.homecooked.common.exception.NoContentException;
import com.homecooked.common.security.UserService;
import com.homecooked.common.user.UserStatus;
import com.homecooked.common.user.dto.UserResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserService userService;

    @Override
    public ClientResponseDto me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            if (!authorities.isEmpty()) {
                GrantedAuthority firstAuthority = authorities.iterator().next();
                String firstAuthorityString = firstAuthority.getAuthority();
                UserResponseDto me = userService.findByRoleAndEmail(Role.valueOf(firstAuthorityString),
                        userDetails.getUsername());
                Client client = clientMapper.userResponseDtoToEntity(me);
                return clientMapper.toResponseDto(client);
            }
        }
        throw new NoContentException("No authenticated user found");
    }

    @Override
    @Transactional
    public ClientResponseDto update(Integer id, ClientCreateUpdateDto dto) {
        Client client = findById(id);
        if (client.getStatus().equals(UserStatus.DELETED)) {
            throw new EntityDeletedException("Client", id);
        }
        clientMapper.updateEntityFromDto(dto, client);
        Client updatedClientEntity = clientRepository.save(client);
        return clientMapper.toResponseDto(updatedClientEntity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Client client = findById(id);
        client.setStatus(UserStatus.DELETED);
        clientRepository.save(client);
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client not found with id %s", id)));
    }

    @Override
    public Client currentClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = (Client) auth.getPrincipal();
        return client;
    }

}

