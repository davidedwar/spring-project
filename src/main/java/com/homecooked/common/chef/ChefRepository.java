package com.homecooked.common.chef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Integer> {

    @Query("SELECT c FROM Chef c WHERE LOWER(c.email) = LOWER(?1)")
    Optional<Chef> findByEmailIgnoreCase(String email);

    @Query("SELECT c FROM Chef c WHERE LOWER(c.phoneNumber) = LOWER(?1)")
    Optional<Chef> findByPhoneNumber(String phoneNumber);

}
