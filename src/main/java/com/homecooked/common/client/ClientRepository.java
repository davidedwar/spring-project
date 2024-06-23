package com.homecooked.common.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmailIgnoreCase(String email);

    @Query("SELECT c FROM Client c WHERE LOWER(c.phoneNumber) = LOWER(?1)")
    Optional<Client> findByPhoneNumber(String phoneNumber);

}
