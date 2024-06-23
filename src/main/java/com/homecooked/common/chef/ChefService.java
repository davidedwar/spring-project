package com.homecooked.common.chef;

import com.homecooked.common.chef.dto.ChefCreateUpdateDto;
import com.homecooked.common.chef.dto.ChefResponseDto;
import com.homecooked.common.chef.mapper.ChefMapper;
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
public class ChefService {

    private final ChefRepository chefRepository;
    private final ChefMapper chefMapper;
    private final UserService userService;

    public ChefResponseDto me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            if (!authorities.isEmpty()) {
                GrantedAuthority firstAuthority = authorities.iterator().next();
                String firstAuthorityString = firstAuthority.getAuthority();
                UserResponseDto me = userService.findByRoleAndEmail(Role.valueOf(firstAuthorityString),
                        userDetails.getUsername());
                Chef chef = chefMapper.userResponseDtoToEntity(me);
                return chefMapper.toResponseDto(chef);
            }
        }
        throw new NoContentException("No authenticated user found");
    }

    @Transactional
    public ChefResponseDto update(ChefCreateUpdateDto dto) {
        Chef chef = currentChef();
        if (chef.getStatus().equals(UserStatus.DELETED)) {
            throw new EntityDeletedException("Client", chef.getId());
        }
        chefMapper.updateEntityFromDto(dto, chef);
        Chef updatedchefEntity = chefRepository.save(chef);
        return chefMapper.toResponseDto(updatedchefEntity);
    }

    @Transactional
    public void delete(Integer id) {
        Chef chef = findById(id);
        chef.setStatus(UserStatus.DELETED);
        chefRepository.save(chef);
    }

    public Chef findById(Integer id) {
        return chefRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client not found with id %s", id)));
    }

    public Chef currentChef() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Chef chef = (Chef) auth.getPrincipal();
        return chef;
    }


}

