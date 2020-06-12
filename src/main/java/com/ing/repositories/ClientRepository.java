package com.ing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findByIdentityDocNumber(String identityDocNumber);

}
